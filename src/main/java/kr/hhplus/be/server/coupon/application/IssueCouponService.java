package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.config.jpa.RedisKeys;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IssueCouponService {

    private final CouponRepository couponRepository;
    private final MapUserCouponRepository mapUserCouponRepository;
    private final UserRepository userRepository;
    private final CouponRedisRepository couponRedisRepository;


    @Transactional
    public CouponCommandDTO.issueCouponResult issueCoupon(CouponCommandDTO.issueCouponCommand issueCouponCommand) {
        final Long userId = issueCouponCommand.getUserId();
        final Long couponId = issueCouponCommand.getCouponId();

        User user = userRepository.findByUserId(User.builder().userId(userId).build());
        Coupon coupon = couponRepository.findByCouponId(Coupon.builder().couponId(couponId).build());

        MapUserCoupon mapUserCoupon =  mapUserCouponRepository.findByUserIdAndCouponId(
                MapUserCoupon.builder()
                        .userId(user.getUserId())
                        .couponId(coupon.getCouponId())
                        .build()
        );
        mapUserCoupon.alreadyIssued(user.getUserId(), coupon.getCouponId());

        // TODO : refactor 쿠폰 발급 로직에서 Coupon 테이블에 직접 save 없애기
        coupon.issueCoupon();
        Coupon updatedCoupon = couponRepository.save(coupon);
        MapUserCoupon savedMapUserCoupon = mapUserCouponRepository.save(
                MapUserCoupon.builder()
                        .userId(user.getUserId())
                        .couponId(updatedCoupon.getCouponId())
                        .couponName(updatedCoupon.getCouponName())
                        .build()
        );
        return CouponCommandDTO.issueCouponResult.from(savedMapUserCoupon);
    }

    public void joinWaitingList(CouponCommandDTO.issueCouponCommand issueCouponCommand) {
        // 대기열에 쿠폰 발급 신청 추가
        // 중복 발급 신청 방지
        couponRedisRepository.joinWaitingList(RedisKeys.getWaitingCouponIssueKey(issueCouponCommand.getCouponId()), issueCouponCommand.getCouponId(), issueCouponCommand.getUserId(), 1.0);
    }

    public void issuedWaitingList() {
        // 이벤트 중인 CouponId List hash에서 조회

        // List로 각 쿠폰의 발급 가능한 수량 조회 ()
        // 대기열에서 그만큼 뺴기
        //   남은 수량은 db에 update
        //   안남았으면 이벤트 중인 CouponId hash에서 삭제
        // 쿠폰 발급 처리

        List<Long> issuableCouponIds = couponRedisRepository.getAllIssuableCouponIds();
        if(issuableCouponIds.isEmpty()){
            return;
        }
        List<Coupon> issuableCoupons = couponRepository.findAllByCouponIdIn(issuableCouponIds);
        List<Coupon> issueCancelCoupons = new ArrayList<>();

        issuableCoupons.forEach(
                coupon -> {
                    Long result = couponRedisRepository.setAllIssuableCoupons(coupon.getCouponId(), coupon.getIssuableQuantity());
                    if (result == 0) {
                        int issued = coupon.getIssuedQuantity();
                        int issuable = coupon.getIssuableQuantity();
                        coupon.setIssuedQuantity(issued + issuable, 0, 0);
                        couponRepository.save(coupon);
                        couponRedisRepository.deleteIssuableCouponIssue(coupon.getCouponId());
                    } else if (result < 0) {
                        int issued = coupon.getIssuedQuantity();
                        int issuable = coupon.getIssuableQuantity();
                        int remaining = coupon.getRemainingQuantity();
                        // 발급 가능 수량이 8인데 대기열에 들어온 요청은 5였다면 result는 -3이 되어서 -3을 setAllIssuableCoupons에 전달 후 내부에서 - 하므로 쿠폰은 다시 3개 발급 가능하다
                        Long cancel = couponRedisRepository.setAllIssuableCoupons(coupon.getCouponId(), result);
                        coupon.setIssuedQuantity((int) (issued - (issuable - result)),  remaining + cancel.intValue(), cancel.intValue());
                        issueCancelCoupons.add(coupon);
                    }
                }
        );

        issueCancelCoupons.forEach(coupon -> {
            // 발급 취소된 쿠폰에 대해서는 쿠폰 테이블에 업데이트
            couponRepository.save(coupon);
        });

    }
}

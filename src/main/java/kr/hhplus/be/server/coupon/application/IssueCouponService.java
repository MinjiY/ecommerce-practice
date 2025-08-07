package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;

@RequiredArgsConstructor
@Service
public class IssueCouponService implements IssueCoupon {

    private final CouponRepository couponRepository;
    private final MapUserCouponRepository mapUserCouponRepository;
    private final UserRepository userRepository;

    @Override
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
}

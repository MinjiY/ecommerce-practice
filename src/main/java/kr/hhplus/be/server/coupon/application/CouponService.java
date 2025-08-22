package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final MapUserCouponRepository mapUserCouponRepository;

    public CouponCommandDTO.GetAvailableCouponsResult getAvailableCoupons(Long userId) {
        final Long findUserId = userId;
        List<MapUserCoupon> mapUserCoupons = mapUserCouponRepository.findAvailableCouponsByUserIdAndCouponState(
                MapUserCoupon.builder()
                        .userId(findUserId)
                        .couponState(CouponState.ACTIVE)
                        .build()
        );
        return CouponCommandDTO.GetAvailableCouponsResult.from(findUserId, mapUserCoupons);
    }

    @Transactional
    public CouponCommandDTO.canceledCouponResult cancelCoupon(CouponCommandDTO.cancelCouponCommand cancelCouponCommand) {
        MapUserCoupon foundMapUserCoupon = mapUserCouponRepository.findByUserIdAndCouponId(MapUserCoupon.builder()
                .userId(cancelCouponCommand.getUserId())
                .couponId(cancelCouponCommand.getCouponId())
                .build());
        foundMapUserCoupon.cancelCoupon();
        MapUserCoupon canceledCoupon = mapUserCouponRepository.save(foundMapUserCoupon);
        return CouponCommandDTO.canceledCouponResult.from(canceledCoupon);
    }

    @Transactional
    public CouponCommandDTO.useCouponResult useCoupon(CouponCommandDTO.useCouponCommand useCouponCommand) {
        MapUserCoupon foundMapUserCoupon = mapUserCouponRepository.findByUserIdAndCouponId(MapUserCoupon.builder()
                .userId(useCouponCommand.getUserId())
                .couponId(useCouponCommand.getCouponId())
                .build());
        foundMapUserCoupon.useCoupon();
        MapUserCoupon usedCoupon = mapUserCouponRepository.save(foundMapUserCoupon);
        return CouponCommandDTO.useCouponResult.from(usedCoupon);
    }

}

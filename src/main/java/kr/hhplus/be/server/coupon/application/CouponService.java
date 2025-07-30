package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponService {

    private CouponRepository couponRepository;

    public CouponCommandDTO.GetAvailableCouponsResult getAvailableCoupons(Long userId) {
        final Long findUserId = userId;
        List<MapUserCoupon> mapUserCoupons = couponRepository.findAvailableCouponsByUserIdAndCouponState(
                MapUserCoupon.builder()
                        .userId(findUserId)
                        .couponState(CouponState.ACTIVE)
                        .build()
        );
        return CouponCommandDTO.GetAvailableCouponsResult.from(findUserId, mapUserCoupons);
    }

    public CouponCommandDTO.canceledCouponResult cancelCoupon(CouponCommandDTO.cancelCouponCommand cancelCouponCommand) {
        MapUserCoupon foundMapUserCoupon = couponRepository.findByUserIdAndCouponId(MapUserCoupon.builder()
                .userId(cancelCouponCommand.getUserId())
                .couponId(cancelCouponCommand.getCouponId())
                .build());
        foundMapUserCoupon.cancelCoupon();
        MapUserCoupon canceledCoupon = couponRepository.save(foundMapUserCoupon);
        return CouponCommandDTO.canceledCouponResult.from(canceledCoupon);
    }

}

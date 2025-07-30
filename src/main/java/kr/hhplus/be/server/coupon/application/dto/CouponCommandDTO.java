package kr.hhplus.be.server.coupon.application.dto;

import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CouponCommandDTO {


    @Getter
    @Builder
    public static class GetAvailableCouponsResult{
        private Long userId;
        private Integer numberOfAvailableCoupons;
        private List<availableCouponDTO> availableCoupons;

        public static GetAvailableCouponsResult from(Long userId, List<MapUserCoupon> mapUserCoupons){
            return GetAvailableCouponsResult.builder()
                    .userId(userId)
                    .numberOfAvailableCoupons(mapUserCoupons.size())
                    .availableCoupons(mapUserCoupons.stream()
                            .map(availableCouponDTO::from)
                            .toList())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class availableCouponDTO {
        private Long couponId;
        private String couponName;

        public static availableCouponDTO from(MapUserCoupon mapUserCoupon) {
            return availableCouponDTO.builder()
                    .couponId(mapUserCoupon.getCouponId())
                    .couponName(mapUserCoupon.getCouponName())
                    .build();
        }
    }

}

package kr.hhplus.be.server.coupon.application.dto;

import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import lombok.AllArgsConstructor;
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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class issueCouponCommand {
        private Long userId;
        private Long couponId;
    }

    @Getter
    @Builder
    public static class issueCouponResult {
        private Long userId;
        private Long couponId;
        private CouponState couponState;
        private String couponName;

        public static issueCouponResult from(MapUserCoupon mapUserCoupon) {
            return issueCouponResult.builder()
                    .userId(mapUserCoupon.getUserId())
                    .couponId(mapUserCoupon.getCouponId())
                    .couponState(mapUserCoupon.getCouponState())
                    .couponName(mapUserCoupon.getCouponName())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class cancelCouponCommand {
        private Long userId;
        private Long couponId;
    }

    @Getter
    @Builder
    public static class canceledCouponResult {
        private Long userId;
        private Long couponId;
        private CouponState couponState;
        private String couponName;

        public static canceledCouponResult from(MapUserCoupon mapUserCoupon) {
            return canceledCouponResult.builder()
                    .userId(mapUserCoupon.getUserId())
                    .couponId(mapUserCoupon.getCouponId())
                    .couponState(mapUserCoupon.getCouponState())
                    .couponName(mapUserCoupon.getCouponName())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class useCouponCommand {
        private Long userId;
        private Long couponId;
    }

    @Getter
    @Builder
    public static class useCouponResult {
        private Long userId;
        private Long couponId;
        private CouponState couponState;
        private String couponName;

        public static useCouponResult from(MapUserCoupon mapUserCoupon) {
            return useCouponResult.builder()
                    .userId(mapUserCoupon.getUserId())
                    .couponId(mapUserCoupon.getCouponId())
                    .couponState(mapUserCoupon.getCouponState())
                    .couponName(mapUserCoupon.getCouponName())
                    .build();
        }
    }

}

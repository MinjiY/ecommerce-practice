package kr.hhplus.be.server.coupon.domain;

import kr.hhplus.be.server.coupon.common.CouponState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class MapUserCoupon {

    private Long userId;

    private Long couponId;

    @Builder.Default
    private CouponState couponState = CouponState.ACTIVE;

    private String couponName;
}

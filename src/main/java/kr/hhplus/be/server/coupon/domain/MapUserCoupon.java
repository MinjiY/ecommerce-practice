package kr.hhplus.be.server.coupon.domain;

import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.exception.custom.InvalidCouponStateException;
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

    public void cancelCoupon() {
        if(this.couponState == CouponState.USED || this.couponState == CouponState.EXPIRED) {
            throw new InvalidCouponStateException("쿠폰은 이미 사용되었거나 만료되었습니다.");
        }
        this.couponState = CouponState.ACTIVE;
    }
}

package kr.hhplus.be.server.coupon.domain;

import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.exception.ExceptionCode;
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
        if(this.couponState == CouponState.ACTIVE || this.couponState == CouponState.EXPIRED) {
            throw new InvalidCouponStateException("쿠폰은 사용 가능하거나 이미 만료되었습니다.");
        }
        this.couponState = CouponState.ACTIVE;
    }
    public void useCoupon() {
        if(this.couponState != CouponState.ACTIVE) {
            throw new InvalidCouponStateException("쿠폰은 사용 가능한 상태가 아닙니다.");
        }
        this.couponState = CouponState.USED;
    }
    public void alreadyIssued(Long userId, Long couponId) {
        if (this.userId.equals(userId) && this.couponId.equals(couponId)) {
            throw new InvalidCouponStateException(ExceptionCode.ALREADY_ISSUED_COUPON,"이미 발급된 쿠폰입니다. userId: " + userId + ", couponId: " + couponId);
        }
    }

}

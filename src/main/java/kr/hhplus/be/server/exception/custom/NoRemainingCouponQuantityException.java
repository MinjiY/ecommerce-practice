package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class NoRemainingCouponQuantityException extends ecommerceServerException {
    public NoRemainingCouponQuantityException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public NoRemainingCouponQuantityException(String message) {
        super(ExceptionCode.COUPON_NO_REMAINING_QUANTITY, message);
    }

    public NoRemainingCouponQuantityException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public NoRemainingCouponQuantityException() {
        super(ExceptionCode.COUPON_NO_REMAINING_QUANTITY);
    }
}

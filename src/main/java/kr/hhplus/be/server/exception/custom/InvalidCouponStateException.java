package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class InvalidCouponStateException extends ecommerceServerException {
    public InvalidCouponStateException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public InvalidCouponStateException(String message) {
        super(ExceptionCode.INVALID_COUPON_STATE, message);
    }

    public InvalidCouponStateException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidCouponStateException() {
        super(ExceptionCode.INVALID_COUPON_STATE);
    }
}

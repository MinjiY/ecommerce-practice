package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class CouponAlreadyIssuedException extends ecommerceServerException {
    public CouponAlreadyIssuedException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public CouponAlreadyIssuedException(String message) {
        super(ExceptionCode.ALREADY_ISSUED_COUPON, message);
    }

    public CouponAlreadyIssuedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public CouponAlreadyIssuedException() {
        super(ExceptionCode.ALREADY_ISSUED_COUPON);
    }
}

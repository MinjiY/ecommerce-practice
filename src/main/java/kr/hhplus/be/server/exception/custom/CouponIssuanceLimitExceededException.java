package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class CouponIssuanceLimitExceededException extends ecommerceServerException {
    public CouponIssuanceLimitExceededException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public CouponIssuanceLimitExceededException(String message) {
        super(ExceptionCode.COUPON_ISSUANCE_LIMIT_EXCEEDED, message);
    }

    public CouponIssuanceLimitExceededException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public CouponIssuanceLimitExceededException() {
        super(ExceptionCode.COUPON_ISSUANCE_LIMIT_EXCEEDED);
    }
}

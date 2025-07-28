package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class InvalidChargeAmountException extends ecommerceServerException {
    public InvalidChargeAmountException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public InvalidChargeAmountException(String message) {
        super(ExceptionCode.INVALID_CHARGE_AMOUNT, message);
    }

    public InvalidChargeAmountException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidChargeAmountException() {
        super(ExceptionCode.INVALID_CHARGE_AMOUNT);
    }

}

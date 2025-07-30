package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class InvalidWithdrawAmountException extends ecommerceServerException {
    public InvalidWithdrawAmountException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public InvalidWithdrawAmountException(String message) {
        super(ExceptionCode.INVALID_WITHDRAW_AMOUNT, message);
    }

    public InvalidWithdrawAmountException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InvalidWithdrawAmountException() {
        super(ExceptionCode.INVALID_WITHDRAW_AMOUNT);
    }

}

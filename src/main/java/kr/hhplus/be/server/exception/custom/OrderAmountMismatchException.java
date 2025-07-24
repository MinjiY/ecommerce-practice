package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;

public class OrderAmountMismatchException extends ecommerceServerException {
    public OrderAmountMismatchException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public OrderAmountMismatchException(String message) {
        super(ExceptionCode.ORDERE_AMOUNT_MISMATCH, message);
    }

    public OrderAmountMismatchException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public OrderAmountMismatchException() {
        super(ExceptionCode.ORDERE_AMOUNT_MISMATCH);
    }
}

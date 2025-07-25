package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;


public class InsufficientStockException extends ecommerceServerException{

    public InsufficientStockException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public InsufficientStockException(String message) {
        super(ExceptionCode.INSUFFICIENT_STOCK, message);
    }

    public InsufficientStockException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public InsufficientStockException() {
        super(ExceptionCode.INSUFFICIENT_STOCK);
    }

}
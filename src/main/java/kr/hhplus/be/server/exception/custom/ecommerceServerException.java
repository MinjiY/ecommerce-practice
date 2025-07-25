package kr.hhplus.be.server.exception.custom;

import kr.hhplus.be.server.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ecommerceServerException extends RuntimeException{
    private ExceptionCode exceptionCode;

    public ecommerceServerException(ExceptionCode exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
    public ecommerceServerException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
    public ecommerceServerException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}

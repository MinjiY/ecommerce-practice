package kr.hhplus.be.server.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ExceptionResponse {

    private String message;

    private String code;

    private HttpStatus status;

    private String uri;

    private LocalDateTime timestamp;

    public ExceptionResponse(ExceptionCode code, String path) {

        this.message = code.getMessage();

        this.status = code.getStatus();

        this.code = code.getCode();

        this.uri = path;

        this.timestamp = LocalDateTime.now();
    }

    public ExceptionResponse(ExceptionCode code, String path, String message) {

        this.message = message;

        this.status = code.getStatus();

        this.code = code.getCode();

        this.uri = path;

        this.timestamp = LocalDateTime.now();
    }

    public static ExceptionResponse of(ExceptionCode exceptionCode, HttpServletRequest request) {
        return new ExceptionResponse(exceptionCode, request.getRequestURI(), exceptionCode.getMessage());
    }

    public static ExceptionResponse of(ExceptionCode exceptionCode, HttpServletRequest request, String message) {
        return new ExceptionResponse(exceptionCode, request.getRequestURI(), message);
    }

    public static ExceptionResponse of(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return new ExceptionResponse(ExceptionCode.INVALID_INPUT_VALUE, request.getRequestURI(), ex.getMessage());
    }

    public static ExceptionResponse of(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return new ExceptionResponse(ExceptionCode.INVALID_INPUT_VALUE, request.getRequestURI(), ex.getMessage());
    }

    public static ExceptionResponse of(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return new ExceptionResponse(ExceptionCode.INVALID_INPUT_VALUE, request.getRequestURI(), ex.getMessage());
    }

    public static ExceptionResponse of(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return new ExceptionResponse(ExceptionCode.INVALID_INPUT_VALUE, request.getRequestURI(), ex.getMessage());
    }
}


package kr.hhplus.be.server.exception;


import jakarta.servlet.http.HttpServletRequest;
import kr.hhplus.be.server.exception.custom.ecommerceServerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
public class ExceptionControllerAdvice {


    /**
     * Json 파싱 실패시 발생하는 예외 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.of(ex, request);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    /**
     * @RequestParam 쿼리 파라미터 없을 때 발생하는 예외 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.of(ex, request);

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    /**
     * @Valid 검증 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.of(ex, request);

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.METHOD_NOT_ALLOWED, request);

        return ResponseEntity
                .status(response.getStatus())
                .headers(headers)
                .body(response);
    }

    /**
     * Parameter의 Type이 잘못 바인딩 되는 경우 발생
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INVALID_INPUT_VALUE, request, ex.getMessage());
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    /**
     * 그 외 발생하는 모든 예외는 BAD_REQUEST 로 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.of(ExceptionCode.INTERNAL, request, ex.getMessage());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    /**
     * 비즈니스 로직에서 발생시킨 모든 예외 처리
     */
    @ExceptionHandler(ecommerceServerException.class)
    protected ResponseEntity<ExceptionResponse> handleBusinessException(ecommerceServerException ex, HttpServletRequest request) {
        ExceptionResponse response = ExceptionResponse.of(ex.getExceptionCode(), request);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

}


package kr.hhplus.be.server.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MockApiResponse<T> {

    private String message;

    private String code;

    private T data;

    private HttpStatus status;

    private String uri;

    private LocalDateTime timestamp;

    public static <T> MockApiResponse<T> success(T data, String message, HttpServletRequest request) {
        return MockApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.toString())
                .data(data)
                .message(message)
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> MockApiResponse<T> success(T data) {
        return MockApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.toString())
                .data(data)
                .message("요청이 성공적으로 처리되었습니다.")
                .uri(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> MockApiResponse<T> success(String message) {
        return MockApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.toString())
                .data(null)
                .message("요청이 성공적으로 처리되었습니다.")
                .uri(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
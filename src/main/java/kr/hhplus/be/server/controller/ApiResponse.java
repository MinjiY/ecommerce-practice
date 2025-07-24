package kr.hhplus.be.server.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String message;

    private String code;

    private T data;

    private HttpStatus status;

    private String uri;

    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data, String message, HttpServletRequest request) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.toString())
                .data(data)
                .message(message)
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.toString())
                .data(data)
                .message("요청이 성공적으로 처리되었습니다.")
                .uri(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.toString())
                .data(null)
                .message("요청이 성공적으로 처리되었습니다.")
                .uri(null)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
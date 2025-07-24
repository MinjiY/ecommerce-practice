package kr.hhplus.be.server.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    // Global
    INTERNAL(HttpStatus.BAD_REQUEST, "E001", "Internal Error Occurred"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "E002", "Invalid Type Value"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E003", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E004", "Method Not Allowed"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "E005", "Access Denied"),
    PERSONA_IS_NOT_IN_THE_ROOM(HttpStatus.FORBIDDEN, "E006", "Persona Not In This Room"),
    AUTHORIZATION_DENIED(HttpStatus.FORBIDDEN, "E007", "Authorization Denied"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "Resource Not Found"),



    REQUEST_FAILED(HttpStatus.BAD_REQUEST, "P001", "Request Failed");

    // Custom Exception

    private final HttpStatus status;
    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}


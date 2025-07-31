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
    AUTHORIZATION_DENIED(HttpStatus.FORBIDDEN, "E006", "Authorization Denied"),

    // custom
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "Resource Not Found"),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "R002", "Insufficient Stock"),
    ORDERE_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "R003", "Ordered Amount Mismatch"),
    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST, "R004", "Invalid Charge Amount"),
    INVALID_WITHDRAW_AMOUNT(HttpStatus.BAD_REQUEST, "R005", "Invalid Withdraw Amount"),

    INVALID_COUPON_STATE(HttpStatus.BAD_REQUEST, "R006", "Invalid Coupon State"),
    COUPON_ISSUANCE_LIMIT_EXCEEDED(HttpStatus.CONFLICT, "R007", "Coupon Issuance Limit Exceeded"),
    COUPON_NO_REMAINING_QUANTITY(HttpStatus.BAD_REQUEST, "R008", "No Remaining Coupon Quantity"),

    REQUEST_FAILED(HttpStatus.BAD_REQUEST, "P001", "Request Failed");

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


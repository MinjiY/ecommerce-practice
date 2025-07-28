package kr.hhplus.be.server.point.domain;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.InvalidChargeAmountException;
import lombok.Builder;
import lombok.Getter;


@Getter
public class Point {
    private Long pointId;
    private Long balance;
    private Long userId;

    @Builder
    public Point(Long pointId, Long balance, Long userId) {
        this.pointId = pointId;
        this.balance = balance;
        this.userId = userId;
    }

    @Builder
    public Point(Long balance, Long userId) {
        this.balance = balance;
        this.userId = userId;
    }

    public void chargeAmount(Long chargeAmount) {
        if (chargeAmount < 0) {
            throw new InvalidChargeAmountException(ExceptionCode.INVALID_CHARGE_AMOUNT, "충전 금액은 양수여야 합니다.");
        }
        this.balance += chargeAmount;
    }
}
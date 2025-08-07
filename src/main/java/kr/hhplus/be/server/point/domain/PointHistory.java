package kr.hhplus.be.server.point.domain;


import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.InvalidChargeAmountException;
import kr.hhplus.be.server.point.common.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointHistory {
    private Long pointHistoryId;

    private Long userId;
    private Long pointId;
    private Long amount;

    private TransactionType transactionType;

    @Builder
    public PointHistory(Long userId, Long pointId, Long amount, TransactionType transactionType) {
        // 공통 -> 요청 금액이 음수일 수 없음
        // 사용일때 -> 기존 amount - 요청 amount가 음수일 수 없음 => pointHistory가 아니라 Point에서 처리
        if( amount < 0) {
            throw new InvalidChargeAmountException(ExceptionCode.INVALID_CHARGE_AMOUNT, "요청 금액은 음수일 수 없습니다.");
        }
        this.userId = userId;
        this.pointId = pointId;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}

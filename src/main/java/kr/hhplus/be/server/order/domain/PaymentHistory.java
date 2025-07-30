package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.OrderAmountMismatchException;
import kr.hhplus.be.server.order.common.PaymentStatus;
import kr.hhplus.be.server.order.infrastructure.entity.PaymentHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class PaymentHistory {
    private Long paymentHistoryId;

    private Long userId;
    private Long totalAmount;
    @Builder.Default
    private Long discountAmount = 0L;
    private Long paidAmount;
    private Long couponId;

    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.SUCCESS;

    public void paymentAmountMismatch(Long paymentAmount) {
        if (!paymentAmount.equals(totalAmount)) {
            throw new OrderAmountMismatchException(ExceptionCode.ORDERE_AMOUNT_MISMATCH);
        }
    }
    public PaymentHistoryEntity toDomain(){
        return PaymentHistoryEntity.builder()
                .userId(this.userId)
                .totalAmount(this.totalAmount)
                .paymentStatus(this.paymentStatus)
                .discountAmount(this.discountAmount)
                .paidAmount(this.paidAmount)
                .couponId(this.couponId)
                .build();
    }
}

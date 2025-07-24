package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.OrderAmountMismatchException;
import kr.hhplus.be.server.order.common.PaymentStatus;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
public class PaymentHistory {
    private Long paymentHistoryId;

    private Long userId;
    private Long totalAmount;
    private Long discountAmount;
    private Long couponId;

    private PaymentStatus paymentStatus;

    public void paymentAmountMismatch(Long paymentAmount) {
        if (!paymentAmount.equals(totalAmount)) {
            throw new OrderAmountMismatchException(ExceptionCode.ORDERE_AMOUNT_MISMATCH);
        }
    }
}

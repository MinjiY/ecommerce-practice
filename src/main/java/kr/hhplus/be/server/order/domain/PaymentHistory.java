package kr.hhplus.be.server.order.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.hhplus.be.server.order.common.PaymentStatus;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class PaymentHistory {
    private final Long paymentHistoryId;
    private final OrderEntity order;

    private final Long userId;
    private final Long amount;
    private final Long discountAmount;
    private final Long couponId;

    private final PaymentStatus paymentStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

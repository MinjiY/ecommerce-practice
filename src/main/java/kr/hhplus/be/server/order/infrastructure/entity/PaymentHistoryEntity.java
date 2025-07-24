package kr.hhplus.be.server.order.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.order.common.PaymentStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "payment_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentHistoryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentHistoryId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    private Long userId;
    private Long amount;
    private Long discountAmount;
    private Long couponId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Builder
    public PaymentHistoryEntity(OrderEntity order, Long userId, Long amount, Long discountAmount, Long couponId, PaymentStatus paymentStatus) {
        this.order = order;
        this.userId = userId;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.couponId = couponId;
        this.paymentStatus = paymentStatus;
    }


}

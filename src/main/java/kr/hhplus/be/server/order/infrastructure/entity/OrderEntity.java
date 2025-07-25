package kr.hhplus.be.server.order.infrastructure.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.order.common.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long totalAmount;
    private Long discountAmount;

    @Builder
    public OrderEntity(Long userId, OrderStatus orderStatus, Long totalAmount, Long discountAmount) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
    }

    // for Test
    @Profile("test")
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

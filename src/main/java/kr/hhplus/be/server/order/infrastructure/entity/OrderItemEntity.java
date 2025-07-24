package kr.hhplus.be.server.order.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    private Long userId;
    private String productName;
    private Long productAmount;
    private Integer orderQuantity;
    private Long productId;


    @Builder
    public OrderItemEntity(OrderEntity order, Long userId, String productName, Long productAmount, Integer orderQuantity, Long productId) {
        this.order = order;
        this.userId = userId;
        this.productName = productName;
        this.productAmount = productAmount;
        this.orderQuantity = orderQuantity;
        this.productId = productId;
    }

    public OrderItemEntity setOrderEntity(OrderEntity orderEntity) {
        this.order = orderEntity;
        return this;
    }
}

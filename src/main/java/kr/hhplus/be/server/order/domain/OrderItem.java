package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    private final Long userId;
    private final String productName;
    private final Long productAmount;
    private final Integer orderQuantity;
    private final Long productId;
}

package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderItems {
    private final Long orderItemId;
    private final OrderEntity order;
    private final Long userId;
    private final String productName;
    private final Long productAmount;
    private final Integer orderQuantity;
    private final Long productId;
}

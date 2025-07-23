package kr.hhplus.be.server.order.domain;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class Orders {
    private final Long orderId;
    private final Long userId;
    private final String orderStatus;
    private final Long totalAmount;
    private final Long discountAmount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}

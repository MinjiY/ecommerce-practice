package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.order.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private final Long orderId;
    private final Long userId;
    private final OrderStatus orderStatus;
    private final Long totalAmount;
    private final Long discountAmount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}

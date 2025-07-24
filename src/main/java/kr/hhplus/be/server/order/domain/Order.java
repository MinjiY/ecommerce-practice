package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.OrderAmountMismatchException;
import kr.hhplus.be.server.order.common.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private final Long orderId;
    private final Long userId;
    @Builder.Default
    private final OrderStatus orderStatus = OrderStatus.COMPLETED; // 주문 상태
    private final Long totalAmount; // 총 결제금액
    @Builder.Default
    private final Long discountAmount = 0L; // 할인 금액
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public void orderedAmountMismatch(List<OrderItem> orderItems) {
        Long orderedAmount = orderItems.stream().map(OrderItem::getCalculateAmount).reduce(0L, Long::sum);
        if (!orderedAmount.equals(totalAmount)) {
            throw new OrderAmountMismatchException(ExceptionCode.ORDERE_AMOUNT_MISMATCH);
        }
    }

}

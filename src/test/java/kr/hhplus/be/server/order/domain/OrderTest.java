package kr.hhplus.be.server.order.domain;

import kr.hhplus.be.server.exception.custom.OrderAmountMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @DisplayName("주문 아이템의 총 금액이 주문의 총 금액과 일치하지 않으면 OrderAmountMismatchException 예외를 발생시킨다.")
    @Test
    void orderedAmountMismatch() {
        // given
        Long userId = 123L;
        Long firstProductId = 45L;
        Long secondProductId = 46L;
        String firstProductName = "JVM 밑바닥까지 파헤치기";
        String secondProductName = "Effective Java";
        Long firstProductAmount = 15000L;
        Long secondProductAmount = 10000L;
        Integer firstOrderQuantity = 1;
        Integer secondOrderQuantity = 2;
        Long expectedTotalAmount = (firstProductAmount * firstOrderQuantity) + (secondProductAmount * secondOrderQuantity);
        Long failedTotalAmount = expectedTotalAmount + 1000L; // 총 금액이 일치하지 않도록 설정

        OrderItem firstOrderItem = OrderItem.builder()
                .userId(userId)
                .productName(firstProductName)
                .productAmount(firstProductAmount)
                .orderQuantity(firstOrderQuantity)
                .productId(firstProductId)
                .build();
        OrderItem secondOrderItem = OrderItem.builder()
                .userId(userId)
                .productName(secondProductName)
                .productAmount(secondProductAmount)
                .orderQuantity(secondOrderQuantity)
                .productId(secondProductId)
                .build();
        List<OrderItem> orderItems = List.of(firstOrderItem, secondOrderItem);
        Order order = Order.builder()
                .userId(userId)
                .totalAmount(failedTotalAmount)
                .build();

        // when & then
        assertThrows(OrderAmountMismatchException.class, () -> {
            order.orderedAmountMismatch(orderItems);
        });
    }

}
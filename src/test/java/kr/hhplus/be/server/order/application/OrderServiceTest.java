package kr.hhplus.be.server.order.application;

import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.common.OrderStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;


    @DisplayName("주문을 생성한다.")
    @Test
    void createOrder() {
        long userId = 123L;
        long orderId = 456L;
        long firstProductId = 78L;
        long secondProductId = 79L;
        long firstProductAmount = 15000L;
        long secondProductAmount = 10000L;
        long totalAmount = firstProductAmount + secondProductAmount;

        Order expectedOrder = Order.builder()
                .userId(userId)
                .orderId(orderId)
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.COMPLETED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(orderRepository.save(expectedOrder)).thenReturn(expectedOrder);

        // when
        Order savedOrder = orderRepository.save(expectedOrder);

        // then
        verify(orderRepository).save(expectedOrder);
        assertAll(
            () -> assertNotNull(savedOrder, "저장된 주문이 null이 아닙니다."),
            () -> assertEquals(expectedOrder.getOrderId(), savedOrder.getOrderId(), "주문 ID가 일치합니다."),
            () -> assertEquals(expectedOrder.getUserId(), savedOrder.getUserId(), "사용자 ID가 일치합니다."),
            () -> assertEquals(expectedOrder.getTotalAmount(), savedOrder.getTotalAmount(), "총 결제 금액이 일치합니다."),
            () -> assertEquals(expectedOrder.getOrderStatus(), savedOrder.getOrderStatus(), "주문 상태가 일치합니다.")
        );
    }
}
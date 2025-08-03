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
                .orderedAt(LocalDateTime.now())
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

    @DisplayName("주문을 주문상품과 함께 생성한다.")
    @Test
    void createOrderWithOrderItems() {
        // given
        long userId = 123L;
        long orderId = 456L;
        long firstProductId = 78L;
        long secondProductId = 79L;
        long firstProductAmount = 15000L;
        long secondProductAmount = 10000L;
        String firstProductName = "JVM 밑바닥까지 파헤치기";
        String secondProductName = "Effective Java";
        int firstProductQuantity = 2;
        int secondProductQuantity = 1;
        long productTotalAmount = (firstProductAmount * firstProductQuantity) + (secondProductAmount * secondProductQuantity);
        Order order = Order.builder()
                .userId(userId)
                .orderId(orderId)
                .totalAmount(productTotalAmount)
                .paidAmount(productTotalAmount)
                .build();
        List<OrderItem> orderItems = List.of(
                OrderItem.builder()
                        .userId(userId)
                        .productId(firstProductId)
                        .productName(firstProductName)
                        .productAmount(firstProductAmount)
                        .orderQuantity(firstProductQuantity)
                        .build(),
                OrderItem.builder()
                        .userId(userId)
                        .productId(secondProductId)
                        .productName(secondProductName)
                        .productAmount(secondProductAmount)
                        .orderQuantity(secondProductQuantity)
                        .build()
        );

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemRepository.saveAll(any(Order.class), any(List.class))).thenReturn(orderItems);

        OrderCommandDTO.CreateOrderCommand createOrderCommand = OrderCommandDTO.CreateOrderCommand.builder()
                .userId(userId)
                .orderedAmount(productTotalAmount)
                .discountAmount(0L) // 할인 금액은 0으로 설정
                .orderedProducts(List.of(
                        OrderCommandDTO.CreateOrderItemCommand.builder()
                                .userId(userId)
                                .productId(firstProductId)
                                .productName(firstProductName)
                                .productAmount(firstProductAmount)
                                .orderQuantity(firstProductQuantity)
                                .build(),
                        OrderCommandDTO.CreateOrderItemCommand.builder()
                                .userId(userId)
                                .productId(secondProductId)
                                .productName(secondProductName)
                                .productAmount(secondProductAmount)
                                .orderQuantity(secondProductQuantity)
                                .build()
                ))
                .build();

        // when
        OrderCommandDTO.CreateOrderResult createOrderResult =  orderService.createOrder(createOrderCommand);

        //then
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).saveAll(any(Order.class), any(List.class));
        assertAll(
            () -> assertNotNull(createOrderResult, "주문 생성 결과가 null이 아닙니다."),
            () -> assertEquals(order.getOrderId(), createOrderResult.getOrderId(), "주문 ID가 일치합니다."),
            () -> assertEquals(order.getUserId(), createOrderResult.getUserId(), "사용자 ID가 일치합니다."),
            () -> assertEquals(order.getTotalAmount(), createOrderResult.getOrderedAmount(), "총 결제 금액이 일치합니다."),
            () -> assertEquals(0L, createOrderResult.getDiscountAmount(), "할인 금액은 0입니다."),
            () -> assertEquals(2, createOrderResult.getOrderedProducts().size(), "주문한 상품의 개수가 일치합니다."),
            () -> assertEquals(firstProductId, createOrderResult.getOrderedProducts().get(0).getProductId(), "첫 번째 상품 ID가 일치합니다."),
            () -> assertEquals(firstProductName, createOrderResult.getOrderedProducts().get(0).getProductName(), "첫 번째 상품 이름이 일치합니다."),
            () -> assertEquals(firstProductAmount, createOrderResult.getOrderedProducts().get(0).getProductAmount(), "첫 번째 상품 금액이 일치합니다."),
            () -> assertEquals(firstProductQuantity, createOrderResult.getOrderedProducts().get(0).getOrderQuantity(), "첫 번째 상품 주문 수량이 일치합니다."),
            () -> assertEquals(secondProductId, createOrderResult.getOrderedProducts().get(1).getProductId(), "두 번째 상품 ID가 일치합니다."),
            () -> assertEquals(secondProductName, createOrderResult.getOrderedProducts().get(1).getProductName(), "두 번째 상품 이름이 일치합니다."),
            () -> assertEquals(secondProductAmount, createOrderResult.getOrderedProducts().get(1).getProductAmount(), "두 번째 상품 금액이 일치합니다."),
            () -> assertEquals(secondProductQuantity, createOrderResult.getOrderedProducts().get(1).getOrderQuantity(), "두 번째 상품 주문 수량이 일치합니다."),
            () -> assertEquals(userId, createOrderResult.getOrderedProducts().get(0).getUserId(), "주문자 ID가 일치합니다."),
            () -> assertEquals(userId, createOrderResult.getOrderedProducts().get(1).getUserId(), "주문자 ID가 일치합니다.")
        );
    }
}
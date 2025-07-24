package kr.hhplus.be.server.order.infrastructure.entity;

import kr.hhplus.be.server.order.common.OrderStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.application.OrderRepository;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("주문 엔티티의 영속성을 잘 지키고 있는지, 주문 도메인과 매핑이 잘 되는지 테스트한다.")
@ExtendWith(MockitoExtension.class)
class OrderEntityTest {

    @Mock
    private OrderRepository orderRepository;

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Test
    @DisplayName("주문 엔티티가 도메인에 정상적으로 매핑된다.")
    void testOrderEntityToOrder() {
        // given
        OrderEntity orderEntity = OrderEntity.builder()
                .userId(1L)
                .orderStatus(OrderStatus.COMPLETED)
                .totalAmount(10000L)
                .discountAmount(100L)
                .build();

        // when
        Order order = orderMapper.entityToDomain(orderEntity);

        // then
        assertAll(
            () -> assertNotNull(order),
            () -> assertEquals(orderEntity.getOrderId(), order.getOrderId()),
            () -> assertEquals(orderEntity.getUserId(), order.getUserId()),
            () -> assertEquals(orderEntity.getOrderStatus(), order.getOrderStatus()),
            () -> assertEquals(orderEntity.getTotalAmount(), order.getTotalAmount()),
            () -> assertEquals(orderEntity.getDiscountAmount(), order.getDiscountAmount())
        );
    }

    @Test
    @DisplayName("주문 도메인이 엔티티에 정상적으로 매핑된다.")
    void testOrderToOrderEntity() {
        // given
        Order order = Order.builder()
                .userId(1L)
                .orderStatus(OrderStatus.COMPLETED)
                .totalAmount(5000L)
                .discountAmount(500L)
                .build();

        // when
        OrderEntity orderEntity = orderMapper.domainToEntity(order);

        // then
        assertAll(
            () -> assertNotNull(orderEntity),
            () -> assertEquals(order.getUserId(), orderEntity.getUserId()),
            () -> assertEquals(order.getOrderStatus(), orderEntity.getOrderStatus()),
            () -> assertEquals(order.getTotalAmount(), orderEntity.getTotalAmount()),
            () -> assertEquals(order.getDiscountAmount(), orderEntity.getDiscountAmount())
        );
    }

    @Test
    @DisplayName("주문상품 엔티티가 도메인에 정상적으로 매핑된다.")
    void testOrderItemEntityToOrderItem() {
        // given
        long orderId = 100L;
        OrderEntity orderEntity = OrderEntity.builder()
                .userId(1L)
                .orderStatus(OrderStatus.COMPLETED)
                .totalAmount(10000L)
                .discountAmount(100L)
                .build();
        orderEntity.setOrderId(orderId); // for test

        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                .productId(100L)
                .productName("Test Product")
                .productAmount(2000L)
                .orderQuantity(2)
                .build();
        orderItemEntity.setOrderEntity(orderEntity);

        // when
        OrderItem orderItem = orderMapper.entityToOrderItemDomain(orderItemEntity);

        // then
        assertAll(
            () -> assertNotNull(orderItem),
            () -> assertEquals(orderItemEntity.getOrder().getOrderId(), orderEntity.getOrderId()),
            () -> assertEquals(orderItemEntity.getProductId(), orderItem.getProductId()),
            () -> assertEquals(orderItemEntity.getProductName(), orderItem.getProductName()),
            () -> assertEquals(orderItemEntity.getProductAmount(), orderItem.getProductAmount()),
            () -> assertEquals(orderItemEntity.getOrderQuantity(), orderItem.getOrderQuantity())
        );
    }



}
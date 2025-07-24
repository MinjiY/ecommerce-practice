package kr.hhplus.be.server.order.infrastructure.entity;

import kr.hhplus.be.server.order.common.OrderStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.infrastructure.repository.OrderRepository;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import kr.hhplus.be.server.product.mapper.ProductMapper;
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
    @DisplayName("주문 엔티티가 주문 테이블에 정상적으로 저장된다.")
    void testOrderEntitySave() {
        long orderId = 1L;
        // given
        OrderEntity orderEntity = OrderEntity.builder()
                .userId(1L)
                .orderStatus(OrderStatus.COMPLETED)
                .totalAmount(20000L)
                .discountAmount(2000L)
                .build();
        orderEntity.setOrderId(orderId);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);

        // when
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        // then
        assertAll(
            () -> assertNotNull(savedOrderEntity),
            () -> assertEquals(orderId, savedOrderEntity.getOrderId()),
            () -> assertEquals(orderEntity.getUserId(), savedOrderEntity.getUserId()),
            () -> assertEquals(orderEntity.getOrderStatus(), savedOrderEntity.getOrderStatus()),
            () -> assertEquals(orderEntity.getTotalAmount(), savedOrderEntity.getTotalAmount()),
            () -> assertEquals(orderEntity.getDiscountAmount(), savedOrderEntity.getDiscountAmount())
        );
    }



}
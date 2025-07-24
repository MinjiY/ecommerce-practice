package kr.hhplus.be.server.order.infrastructure.entity;

import kr.hhplus.be.server.order.common.OrderStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("주문 엔티티의 영속성을 잘 지키고 있는지, 주문 도메인과 매핑이 잘 되는지 테스트한다.")
class OrderEntityTest {

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




}
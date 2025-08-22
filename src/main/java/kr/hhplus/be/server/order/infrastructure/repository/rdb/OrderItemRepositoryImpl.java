package kr.hhplus.be.server.order.infrastructure.repository.rdb;

import kr.hhplus.be.server.order.application.OrderItemRepository;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import kr.hhplus.be.server.order.infrastructure.entity.OrderItemEntity;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public List<OrderItem> saveAll(Order order, List<OrderItem> orderItems) {
        OrderEntity orderEntity = OrderMapper.INSTANCE.domainToOrderEntity(order);
        orderEntity.setOrderId(order.getOrderId());

        List<OrderItemEntity> orderItemEntities = orderItems.stream()
                .map(OrderMapper.INSTANCE::domainToOrderItemEntity)
                .map(orderItemEntity -> orderItemEntity.setOrderEntity(orderEntity))
                .toList();

        return orderItemJpaRepository.saveAll(orderItemEntities).stream()
                .map(OrderMapper.INSTANCE::entityToOrderItemDomain)
                .toList();
    }
}

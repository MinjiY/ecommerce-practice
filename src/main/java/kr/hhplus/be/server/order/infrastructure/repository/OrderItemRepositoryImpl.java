package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.application.OrderItemRepository;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaRepository OrderItemJpaRepository;
    private OrderMapper orderMapper;

    @Override
    public List<OrderItem> saveAll(Order order, List<OrderItem> orderItems) {
        return OrderItemJpaRepository.saveAll(
                orderItems.stream()
                        .map(orderMapper::domainToOrderItemEntity)
                        .map(orderItemEntity -> orderItemEntity.setOrderEntity(
                                OrderMapper.INSTANCE.domainToOrderEntity(order)
                        ))
                        .toList()
        ).stream().map(
                orderMapper::entityToOrderItemDomain
        ).toList();
    }
}

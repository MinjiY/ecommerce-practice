package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.application.OrderItemRepository;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.infrastructure.entity.OrderItemEntity;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final JpaRepository<OrderItemEntity, Long> OrderItemJpaRepository;
    private final OrderMapper orderMapper;

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

package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.application.OrderRepository;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaRepository<OrderEntity, Long> OrderJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order save(Order order){
        return orderMapper.entityToDomain(OrderJpaRepository.save(orderMapper.domainToEntity(order)));
    }
}

package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.application.OrderRepository;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private OrderMapper orderMapper;

    @Override
    public Order save(Order order){
        return orderMapper.entityToDomain(orderJpaRepository.save(orderMapper.domainToEntity(order)));
    }
}

package kr.hhplus.be.server.order.application;


import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderRepository{
    Order save(Order order);
}

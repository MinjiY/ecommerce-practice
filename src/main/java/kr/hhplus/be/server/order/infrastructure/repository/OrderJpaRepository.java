package kr.hhplus.be.server.order.infrastructure.repository;

import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}


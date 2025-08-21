package kr.hhplus.be.server.order.infrastructure.repository.rdb;

import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserId(Long userId);
}


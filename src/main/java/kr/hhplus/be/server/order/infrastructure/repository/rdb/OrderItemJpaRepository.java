package kr.hhplus.be.server.order.infrastructure.repository.rdb;

import kr.hhplus.be.server.order.infrastructure.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {

}

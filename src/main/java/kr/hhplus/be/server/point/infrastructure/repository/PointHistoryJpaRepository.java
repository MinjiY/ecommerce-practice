package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.point.infrastructure.entity.PointHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryJpaRepository extends JpaRepository<PointHistoryEntity, Long> {
}

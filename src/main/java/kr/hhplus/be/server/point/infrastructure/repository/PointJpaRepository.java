package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {

    @Query("SELECT p FROM PointEntity p WHERE p.userId = :userId")
    Optional<PointEntity> findByUserId(Long userId);

}

package kr.hhplus.be.server.point.infrastructure.repository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointJpaRepository extends JpaRepository<PointEntity, Long> {

    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PointEntity p WHERE p.userId = :userId")
    Optional<PointEntity> findByUserId(Long userId);

    @Query("SELECT p FROM PointEntity p WHERE p.userId = :userId")
    Optional<PointEntity> findByUserIdForTest(Long userId);

}

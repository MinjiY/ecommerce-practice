package kr.hhplus.be.server.coupon.infrastructure.repository.rdb;

import kr.hhplus.be.server.coupon.infrastructure.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    List<CouponEntity> findAllByCouponIdIn(List<Long> couponIds);
}

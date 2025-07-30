package kr.hhplus.be.server.coupon.infrastructure.repository;

import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponJpaRepositoryImpl extends JpaRepository<MapUserCouponEntity, MapUserCouponId> {

    List<MapUserCouponEntity> findAllByUserIdAndCouponState(Long userId, String couponState);
}

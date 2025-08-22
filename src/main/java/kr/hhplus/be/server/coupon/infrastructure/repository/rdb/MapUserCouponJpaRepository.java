package kr.hhplus.be.server.coupon.infrastructure.repository.rdb;

import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapUserCouponJpaRepository extends JpaRepository<MapUserCouponEntity, MapUserCouponId> {

    List<MapUserCouponEntity> findAllByUserIdAndCouponState(Long userId, CouponState couponState);

    Optional<MapUserCouponEntity> findByUserIdAndCouponId(Long userId, Long couponId);

}

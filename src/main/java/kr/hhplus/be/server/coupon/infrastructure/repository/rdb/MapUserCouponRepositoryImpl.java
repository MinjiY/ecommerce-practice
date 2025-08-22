package kr.hhplus.be.server.coupon.infrastructure.repository.rdb;

import kr.hhplus.be.server.coupon.application.MapUserCouponRepository;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MapUserCouponRepositoryImpl implements MapUserCouponRepository {

    private final MapUserCouponJpaRepository mapUserCouponJpaRepository;

    // 사용 가능한 쿠폰 목록 조회
    @Transactional(readOnly = true)
    @Override
    public List<MapUserCoupon> findAvailableCouponsByUserIdAndCouponState(MapUserCoupon mapUserCoupon){
        List<MapUserCouponEntity> mapUserCouponEntities = mapUserCouponJpaRepository.findAllByUserIdAndCouponState(mapUserCoupon.getUserId(), mapUserCoupon.getCouponState());
        return mapUserCouponEntities.stream()
                .map(CouponMapper.INSTANCE::entityToMapUserCouponDomainWithoutUserId)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public MapUserCoupon findByUserIdAndCouponId(MapUserCoupon mapUserCoupon){
        MapUserCouponEntity mapUserCouponEntity = mapUserCouponJpaRepository.findByUserIdAndCouponId(
                mapUserCoupon.getUserId(),
                mapUserCoupon.getCouponId()
        ).orElse(MapUserCouponEntity.builder()
                .userId(mapUserCoupon.getUserId())
                .couponId(0L)
                .build());
        return CouponMapper.INSTANCE.entityToMapUserCouponDomain(mapUserCouponEntity);
    }

    @Transactional
    @Override
    public MapUserCoupon save(MapUserCoupon mapUserCoupon) {
        return CouponMapper.INSTANCE.entityToMapUserCouponDomain(mapUserCouponJpaRepository.save(CouponMapper.INSTANCE.domainToMapUserCouponEntity(mapUserCoupon)));
    }

}

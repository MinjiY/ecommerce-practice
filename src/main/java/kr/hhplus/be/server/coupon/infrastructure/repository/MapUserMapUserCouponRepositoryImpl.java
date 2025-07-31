package kr.hhplus.be.server.coupon.infrastructure.repository;

import kr.hhplus.be.server.coupon.application.MapUserCouponRepository;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MapUserMapUserCouponRepositoryImpl implements MapUserCouponRepository {

    private final MapUserCouponJpaRepositoryImpl mapUserCouponJpaRepository;

    private final CouponMapper couponMapper;

    // 사용 가능한 쿠폰 목록 조회
    @Override
    public List<MapUserCoupon> findAvailableCouponsByUserIdAndCouponState(MapUserCoupon mapUserCoupon){
        List<MapUserCouponEntity> mapUserCouponEntities = mapUserCouponJpaRepository.findAllByUserIdAndCouponState(mapUserCoupon.getUserId(), mapUserCoupon.getCouponState());
        return mapUserCouponEntities.stream()
                .map(CouponMapper.INSTANCE::entityToMapUserCouponDomainWithoutUserId)
                .toList();
    }

    @Override
    public MapUserCoupon findByUserIdAndCouponId(MapUserCoupon mapUserCoupon){
        MapUserCouponEntity mapUserCouponEntity = mapUserCouponJpaRepository.findByUserIdAndCouponId(
                mapUserCoupon.getUserId(),
                mapUserCoupon.getCouponId()
        ).orElseThrow(() -> new ResourceNotFoundException("쿠폰을 찾을 수 없습니다."));
        return CouponMapper.INSTANCE.entityToMapUserCouponDomain(mapUserCouponEntity);
    }

    @Override
    public MapUserCoupon save(MapUserCoupon mapUserCoupon) {
        return couponMapper.entityToMapUserCouponDomain(mapUserCouponJpaRepository.save(couponMapper.domainToMapUserCouponEntity(mapUserCoupon)));
    }

}

package kr.hhplus.be.server.coupon.infrastructure.repository;

import kr.hhplus.be.server.coupon.application.CouponRepository;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepositoryImpl mapUserCouponJpaRepository;

    // 사용 가능한 쿠폰 목록 조회
    @Override
    public List<MapUserCoupon> findAvailableCouponsByUserIdAndCouponState(MapUserCoupon mapUserCoupon){
        List<MapUserCouponEntity> mapUserCouponEntities = mapUserCouponJpaRepository.findAllByUserIdAndCouponState(mapUserCoupon.getUserId(), mapUserCoupon.getCouponState().toString());
        return mapUserCouponEntities.stream()
                .map(CouponMapper.INSTANCE::entityToMapUserCouponDomainWithoutUserId)
                .toList();
    }
}

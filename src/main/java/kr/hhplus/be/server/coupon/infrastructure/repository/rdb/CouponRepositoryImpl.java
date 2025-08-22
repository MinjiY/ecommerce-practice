package kr.hhplus.be.server.coupon.infrastructure.repository.rdb;

import kr.hhplus.be.server.coupon.application.CouponRepository;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.CouponEntity;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CouponRepositoryImpl implements CouponRepository {
    private final CouponJpaRepository couponJpaRepository;

    @Transactional(readOnly = true)
    @Override
    public Coupon findByCouponId(Coupon coupon) {
        return CouponMapper.INSTANCE.entityToCouponDomain(
                couponJpaRepository.findById(coupon.getCouponId())
                        .orElseThrow(() -> new ResourceNotFoundException("쿠폰을 찾을 수 없습니다. id: " + coupon.getCouponId()))
        );
    }

    @Transactional
    @Override
    public Coupon save(Coupon coupon){
        CouponEntity couponEntity = CouponMapper.INSTANCE.domainToCouponEntity(coupon);
        return CouponMapper.INSTANCE.entityToCouponDomain(couponJpaRepository.save(couponEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Coupon> findAllByCouponIdIn(List<Long> couponIds){
        List<CouponEntity> couponEntities = couponJpaRepository.findAllByCouponIdIn(couponIds);
        return couponEntities.stream()
                        .map(CouponMapper.INSTANCE::entityToCouponDomain)
                .collect(Collectors.toList());

    }


}


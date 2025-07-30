package kr.hhplus.be.server.mapper;

import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CouponMapper {

    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    MapUserCoupon entityToMapUserCouponDomain(MapUserCouponEntity mapUserCouponEntity);

    MapUserCoupon entityToMapUserCouponDomainWithoutUserId(MapUserCouponEntity mapUserCouponEntity);

    MapUserCouponEntity domainToMapUserCouponEntity(MapUserCoupon mapUserCoupon);

}

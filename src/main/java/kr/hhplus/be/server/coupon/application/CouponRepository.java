package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;

import java.util.List;

public interface CouponRepository {
    // 사용 가능한 쿠폰 목록 조회
    List<MapUserCoupon> findAvailableCouponsByUserIdAndCouponState(MapUserCoupon mapUserCoupon);
}

package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.domain.Coupon;

public interface CouponRepository {
    Coupon findByCouponId(Coupon coupon);
    Coupon save(Coupon coupon);
}

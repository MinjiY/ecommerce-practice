package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.domain.Coupon;

import java.util.List;

public interface CouponRepository {
    Coupon findByCouponId(Coupon coupon);
    Coupon save(Coupon coupon);
    List<Coupon> findAllByCouponIdIn(List<Long> couponIds);
}

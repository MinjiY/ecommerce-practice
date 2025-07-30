package kr.hhplus.be.server.coupon.domain;

import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.exception.custom.InvalidCouponStateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapUserCouponTest {

    @DisplayName("이미 사용되었거나 만료된 쿠폰을 취소하면 InvalidCouponStateException이 발생한다.")
    @Test
    void cancelCouponWhenUsedAndExpired() {
        // given
        MapUserCoupon usedCoupon = MapUserCoupon.builder()
                .userId(1L)
                .couponId(1L)
                .couponState(CouponState.USED)
                .couponName("30% 할인 쿠폰")
                .build();

        MapUserCoupon expiredCoupon = MapUserCoupon.builder()
                .userId(2L)
                .couponId(2L)
                .couponState(CouponState.EXPIRED)
                .couponName("신규회원 30% 할인 쿠폰")
                .build();

        // when and then
        assertThrows(InvalidCouponStateException.class, usedCoupon::cancelCoupon);
        assertThrows(InvalidCouponStateException.class, expiredCoupon::cancelCoupon);
    }

    @DisplayName("이미 사용되었거나 만료된 쿠폰을 사용하면 InvalidCouponStateException이 발생한다.")
    @Test
    void useCouponWhenUsedAndExpired() {
        // given
        MapUserCoupon usedCoupon = MapUserCoupon.builder()
                .userId(1L)
                .couponId(1L)
                .couponState(CouponState.USED)
                .couponName("30% 할인 쿠폰")
                .build();

        MapUserCoupon expiredCoupon = MapUserCoupon.builder()
                .userId(2L)
                .couponId(2L)
                .couponState(CouponState.EXPIRED)
                .couponName("신규회원 30% 할인 쿠폰")
                .build();

        // when and then
        assertThrows(InvalidCouponStateException.class, usedCoupon::useCoupon);
        assertThrows(InvalidCouponStateException.class, expiredCoupon::useCoupon);
    }
}
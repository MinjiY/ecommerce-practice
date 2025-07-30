package kr.hhplus.be.server.coupon.infrastructure.entity;

import lombok.EqualsAndHashCode;

import java.util.Objects;


public class MapUserCouponId{
    private Long user;
    private Long coupon;

    public MapUserCouponId() {}

    public MapUserCouponId(Long user, Long coupon) {
        this.user = user;
        this.coupon = coupon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapUserCouponId that = (MapUserCouponId) o;
        return Objects.equals(user, that.user) && Objects.equals(coupon, that.coupon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, coupon);
    }
}

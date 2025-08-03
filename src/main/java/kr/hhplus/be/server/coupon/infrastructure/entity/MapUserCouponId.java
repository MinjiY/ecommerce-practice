package kr.hhplus.be.server.coupon.infrastructure.entity;

import lombok.EqualsAndHashCode;

import java.util.Objects;


public class MapUserCouponId{
    private Long userId;
    private Long couponId;

    public MapUserCouponId() {}

    public MapUserCouponId(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }


}

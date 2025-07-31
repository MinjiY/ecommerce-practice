package kr.hhplus.be.server.coupon.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAP_USER_COUPON")
@IdClass(MapUserCouponId.class)
@NoArgsConstructor
public class MapUserCouponEntity {

    @Id
    private Long userId;

    @Id
    private Long couponId;

    @Enumerated(EnumType.STRING)
    private CouponState couponState;

    private String couponName;


    @Builder
    public MapUserCouponEntity(Long userId, Long couponId, CouponState couponState, String couponName) {
        this.userId = userId;
        this.couponId = couponId;
        this.couponState = couponState;
        this.couponName = couponName;
    }

}
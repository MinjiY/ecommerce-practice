package kr.hhplus.be.server.coupon.infrastructure.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.user.entity.UserEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAP_USER_COUPON")
@IdClass(MapUserCouponId.class)
@NoArgsConstructor
public class MapUserCouponEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;


    @Enumerated(EnumType.STRING)
    private CouponState couponState;


    @Builder
    public MapUserCouponEntity(UserEntity user, CouponEntity coupon) {
        this.user = user;
        this.coupon = coupon;
    }

}
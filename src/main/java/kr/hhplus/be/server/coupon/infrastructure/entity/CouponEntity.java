package kr.hhplus.be.server.coupon.infrastructure.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.coupon.common.CouponState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private String couponCode;

    @Enumerated(EnumType.STRING)
    private CouponState couponState;

    private Integer expirationDays;

    private Integer issuableQuantity;

    private Integer remainingQuantity;

    private BigDecimal discountRate;

}

package kr.hhplus.be.server.coupon.infrastructure.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.coupon.common.CouponState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    private LocalDate expirationDate;

    private Integer issuableQuantity;

    private Integer remainingQuantity;

    private BigDecimal discountRate;

}

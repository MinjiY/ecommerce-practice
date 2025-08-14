package kr.hhplus.be.server.coupon.infrastructure.entity;


import jakarta.persistence.*;
import kr.hhplus.be.server.coupon.common.CouponState;
import lombok.AccessLevel;
import lombok.Builder;
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

    private String couponName;

    private LocalDate expirationDate;

    private Integer issuableQuantity;

    private Integer issuedQuantity;

    private Integer remainingQuantity;

    private BigDecimal discountRate;

    @Builder
    public CouponEntity(Long couponId, String couponName,LocalDate expirationDate, Integer issuableQuantity, Integer issuedQuantity, Integer remainingQuantity, BigDecimal discountRate) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.expirationDate = expirationDate;
        this.issuableQuantity = issuableQuantity;
        this.issuedQuantity = issuedQuantity;
        this.remainingQuantity = remainingQuantity;
        this.discountRate = discountRate;
    }

}

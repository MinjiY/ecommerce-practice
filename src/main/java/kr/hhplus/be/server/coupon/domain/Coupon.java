package kr.hhplus.be.server.coupon.domain;

import lombok.AllArgsConstructor;

import kr.hhplus.be.server.coupon.common.CouponState;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
public class Coupon {
    private Long couponId;

    private LocalDate expirationDate;

    private Integer issuableQuantity;

    private Integer remainingQuantity;

    private BigDecimal discountRate;
}

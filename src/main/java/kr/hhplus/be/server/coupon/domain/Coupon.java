package kr.hhplus.be.server.coupon.domain;

import lombok.AllArgsConstructor;

import kr.hhplus.be.server.coupon.common.CouponState;

import java.math.BigDecimal;

@AllArgsConstructor
public class Coupon {
    private final Long id;
    private final String code;
    private final CouponState state;
    private final int expirationDays;
    private final int issuableQuantity;
    private final int remainingQuantity;
    private final BigDecimal discountRate;
}

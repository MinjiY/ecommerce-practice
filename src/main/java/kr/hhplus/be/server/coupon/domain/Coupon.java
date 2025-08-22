package kr.hhplus.be.server.coupon.domain;

import kr.hhplus.be.server.exception.custom.NoRemainingCouponQuantityException;
import lombok.AllArgsConstructor;

import kr.hhplus.be.server.coupon.common.CouponState;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class Coupon {
    private Long couponId;

    private String couponName;

    private LocalDate expirationDate;

    private Integer issuableQuantity;

    private Integer issuedQuantity;

    private Integer remainingQuantity;

    private BigDecimal discountRate;

    @Builder
    public Coupon(Long couponId, String couponName, LocalDate expirationDate, Integer issuableQuantity,Integer issuedQuantity, Integer remainingQuantity, BigDecimal discountRate) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.expirationDate = expirationDate;
        this.issuableQuantity = issuableQuantity;
        this.issuedQuantity = issuedQuantity;
        this.remainingQuantity = remainingQuantity;
        this.discountRate = discountRate;
    }

    public void issueCoupon() {
        validateCouponForIssuance();
        this.issuedQuantity++;
        this.remainingQuantity--;
    }
    public void setIssuedQuantity(Integer issuedQuantity, Integer remainingQuantity, Integer issuableQuantity) {
        this.issuedQuantity = issuedQuantity;
        this.remainingQuantity = remainingQuantity;
        this.issuableQuantity = issuableQuantity;
    }

    public void validateCouponForIssuance() {
        if (this.remainingQuantity <= 0) {
            throw new NoRemainingCouponQuantityException("쿠폰 발급 가능 수량이 부족합니다. 쿠폰 ID: " + couponId);
        }
    }

}

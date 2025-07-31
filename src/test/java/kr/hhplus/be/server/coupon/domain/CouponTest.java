package kr.hhplus.be.server.coupon.domain;

import kr.hhplus.be.server.exception.custom.NoRemainingCouponQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @DisplayName("쿠폰 발급 가능 수량이 부족할 때 NoRemainingCouponQuantityException 예외가 발생한다.")
    @Test
    public void validateCouponForIssuance(){
        // give
        Coupon coupon = Coupon.builder()
                .couponId(1L)
                .couponName("50% 할인 쿠폰")
                .expirationDate(LocalDate.now().plusDays(30))
                .issuableQuantity(10)
                .issuedQuantity(10)
                .remainingQuantity(0)
                .discountRate(new BigDecimal("0.5"))
                .build();

        // when & then
        assertThrows(NoRemainingCouponQuantityException.class, coupon::validateCouponForIssuance);
    }

}
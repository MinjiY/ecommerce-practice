package kr.hhplus.be.server.point.domain;

import kr.hhplus.be.server.exception.custom.InvalidChargeAmountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @DisplayName("포인트 충전시 기존 잔액에 충전 요청 금액을 더한 값을 반환해야한다.")
    @Test
    void pointChargeTest() {
        // given
        Point point = Point.builder()
                .balance(1000L)
                .userId(1L)
                .build();

        // when
        point.chargeAmount(500L);

        // then
        assertEquals(1500L, point.getBalance());
    }

    @DisplayName("충전 금액이 음수일 경우 InvalidChargeAmountException 예외가 발생해야 한다.")
    @Test
    void pointChargeNegativeAmountTest() {
        // given
        Point point = Point.builder()
                .balance(1000L)
                .userId(1L)
                .build();

        assertThrows(InvalidChargeAmountException.class, () -> {
            // when
            point.chargeAmount(-500L);
        });
    }
}
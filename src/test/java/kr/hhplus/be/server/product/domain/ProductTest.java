package kr.hhplus.be.server.product.domain;

import kr.hhplus.be.server.exception.custom.InsufficientStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @DisplayName("상품의 재고가 주문 요청 수량보다 작으면 실패한다.")
    @Test
    void decreaseQuantityFail() {
        int quantityToDecrease = 5;
        int remainingQuantity = 1;

        // given
        Product product = Product.builder()
                .productId(1L)
                .name("도메인 주도 개발 시작하기")
                .description("도메인 주도 개발의 기초를 배우는 책입니다.")
                .price(10000L)
                .category("IT")
                .quantity(remainingQuantity)
                .build();

        // then
        assertThrows(InsufficientStockException.class, () -> {
                    product.decreaseQuantity(quantityToDecrease);
        });
        assertEquals(remainingQuantity, product.getQuantity(), "수량이 변경되지 않아야 합니다.");
    }
}
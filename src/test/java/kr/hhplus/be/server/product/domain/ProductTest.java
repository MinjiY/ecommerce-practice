package kr.hhplus.be.server.product.domain;

import kr.hhplus.be.server.exception.custom.InsufficientStockException;
import kr.hhplus.be.server.product.common.ProductState;
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

    @DisplayName("상품의 재고가 주문 요청 수량보다 크면 성공한다.")
    @Test
    void decreaseQuantitySuccess() {
        // given
        int remainingQuantity = 10;
        int quantityToDecrease = 5;
        int expectedQuantity = remainingQuantity- quantityToDecrease;
        Product product = Product.builder()
                .productId(1L)
                .name("도메인 주도 개발 시작하기")
                .description("도메인 주도 개발의 기초를 배우는 책입니다.")
                .price(10000L)
                .category("IT")
                .quantity(remainingQuantity)
                .build();

        // when
        product.decreaseQuantity(quantityToDecrease);

        // then
        assertEquals(expectedQuantity, product.getQuantity(), "상품의 수량이 감소해야 합니다.");
    }

    @DisplayName("재고 차감 후, 상품의 재고가 0개면 품절 상태로 변경된다.")
    @Test
    void ProductStateSoldOut() {
        int quantityToDecrease = 5;
        int remainingQuantity = 5;
        int expectedQuantity = remainingQuantity - quantityToDecrease;
        // given
        Product product = Product.builder()
                .productId(1L)
                .name("도메인 주도 개발 시작하기")
                .description("도메인 주도 개발의 기초를 배우는 책입니다.")
                .price(10000L)
                .category("IT")
                .quantity(remainingQuantity)
                .build();

        //when
        product.decreaseQuantity(quantityToDecrease);

        // then
        assertEquals(ProductState.SOLD_OUT, product.getProductState(), "상품 상태는 SOLD_OUT이어야 한다.");
        assertEquals(expectedQuantity, product.getQuantity(), "상품의 수량은 0이어야 한다.");
    }
}
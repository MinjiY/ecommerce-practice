package kr.hhplus.be.server.product.domain;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.InsufficientStockException;
import kr.hhplus.be.server.product.common.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class Product {
    private Long productId;
    private String name;
    private String description;
    private String category;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer quantity;

    private ProductState productState;

    public Product decreaseQuantity(Integer quantity) {
        if (this.quantity < quantity) {
            throw new InsufficientStockException(ExceptionCode.INVALID_INPUT_VALUE);
        }
        this.quantity -= quantity;

        if( this.quantity == 0) {
            setProductStateSoldOut();
        }
        return this;
    }

    public void setProductStateSoldOut() {
        this.productState = ProductState.SOLD_OUT;
    }

}

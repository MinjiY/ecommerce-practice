package kr.hhplus.be.server.product.domain;

import kr.hhplus.be.server.product.common.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class Product {
    private final Long productId;
    private final String name;
    private final String description;
    private final String category;
    private final Long price;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Integer quantity;

    private final ProductState productState;
}

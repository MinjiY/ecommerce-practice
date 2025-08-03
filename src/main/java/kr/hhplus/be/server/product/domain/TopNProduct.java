package kr.hhplus.be.server.product.domain;

import kr.hhplus.be.server.product.common.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class TopNProduct {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private String category;
    private Integer quantity;
    private ProductState productState;
    private Long totalSold;

}

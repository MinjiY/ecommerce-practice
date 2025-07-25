package kr.hhplus.be.server.product.application.dto;

import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ProductServiceDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductResult {
        private Long productId;
        private String name;
        private String description;
        private Long price;
        private String category;
        private Integer quantity;
        private ProductState productState;
        private String createdAt;
        private String updatedAt;

        public static ProductResult from(Product product){
            return ProductMapper.INSTANCE.toProductResponse(product);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductListResult {
        private List<ProductResult> products;

        public static List<ProductResult> from(List<Product> products){
            return products.stream()
                    .map(ProductMapper.INSTANCE::toProductResponse)
                    .toList();
        }
    }
}

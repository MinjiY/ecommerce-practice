package kr.hhplus.be.server.product.mapper;


import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Component;

// Mapstruct 들어내는 중
@Component
public class ProductNativeMapper {

    public ProductEntity domainToEntity(Product product){
        return ProductEntity.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory())
                .productState(product.getProductState())
                .quantity(product.getQuantity())
                .build();
    }
    public Product entityToDomain(ProductEntity productEntity) {
        return Product.builder()
                .productId(productEntity.getProductId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .category(productEntity.getCategory())
                .productState(productEntity.getProductState())
                .quantity(productEntity.getQuantity())
                .build();
    }
}


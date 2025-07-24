package kr.hhplus.be.server.product.infrastructure.entity;

import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("상품 엔티티의 영속성을 잘 지키고 있는지, 상품 도메인과 매핑이 잘 되는지 테스트한다.")
class ProductEntityTest {

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @DisplayName("상품 도메인이 엔티티에 정상적으로 매핑된다.")
    @Test
    public void testProductEntity(){
        Product product = Product.builder()
                .name("Test Product")
                .description("This is a test product.")
                .category("Test Category")
                .price(1000L)
                .quantity(10)
                .productState(ProductState.AVAILABLE)
                .build();

        // when
        ProductEntity mappedProductEntity = productMapper.domainToEntity(product);

        // then
        assertNotNull(mappedProductEntity);
        assertEquals(product.getName(), mappedProductEntity.getName());
        assertEquals(product.getDescription(), mappedProductEntity.getDescription());
        assertEquals(product.getCategory(), mappedProductEntity.getCategory());
        assertEquals(product.getPrice(), mappedProductEntity.getPrice());
        assertEquals(product.getQuantity(), mappedProductEntity.getQuantity());
        assertEquals(ProductState.AVAILABLE, mappedProductEntity.getProductState());
    }

}
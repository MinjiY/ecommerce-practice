package kr.hhplus.be.server.product.infrastructure.entity;

import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.repository.ProductRepository;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("상품 엔티티의 영속성을 잘 지키고 있는지, 상품 도메인과 매핑이 잘 되는지 테스트한다.")
class ProductEntityTest {

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Mock
    private ProductRepository productRepository;

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

    @DisplayName("상품 엔티티가 영속성 컨텍스트에 잘 저장되는지 테스트한다.")
    @Test
    public void testProductEntitySave(){
        // given
        ProductEntity productEntity = ProductEntity.builder()
                .name("Test Product")
                .description("This is a test product.")
                .category("Test Category")
                .price(1000L)
                .quantity(10)
                .productState(ProductState.AVAILABLE)
                .build();

        when(productRepository.save(productEntity)).thenReturn(productEntity);

        // when
        ProductEntity savedProductEntity = productRepository.save(productEntity);

        // then
        assertNotNull(savedProductEntity);
        assertEquals(productEntity.getName(), savedProductEntity.getName());
        assertEquals(productEntity.getDescription(), savedProductEntity.getDescription());
        assertEquals(productEntity.getCategory(), savedProductEntity.getCategory());
        assertEquals(productEntity.getPrice(), savedProductEntity.getPrice());
        assertEquals(productEntity.getQuantity(), savedProductEntity.getQuantity());
        assertEquals(ProductState.AVAILABLE, savedProductEntity.getProductState());
    }



}
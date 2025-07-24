package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO.ProductResult;
import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @DisplayName("상품을 조회할 때, 상품이 존재하면 해당 상품 정보를 반환한다.")
    @Test
    void findProduct() {
        // given
        long productId = 1L;
        Product product = Product.builder()
                .productId(productId)
                .productState(ProductState.AVAILABLE)
                .name("자바 네트워크 프로그래밍")
                .description("자바로 배우는 네트워크 프로그래밍의 기초")
                .price(18000L)
                .category("IT")
                .quantity(3)

                .build();
        ProductResult expectedResponse = ProductResult.from(product);

        when(productRepository.findById(productId)).thenReturn(product);

        // when
        ProductResult result = productService.findProduct(productId);

        // then
        verify(productRepository).findById(productId);
        assertAll(
            () -> assertNotNull(result, "상품 조회 결과가 null이 아닙니다."),
            () -> assertEquals(expectedResponse.getProductId(), result.getProductId(), "상품 ID가 일치합니다."),
            () -> assertEquals(expectedResponse.getName(), result.getName(), "상품 이름이 일치합니다."),
            () -> assertEquals(expectedResponse.getDescription(), result.getDescription(), "상품 설명이 일치합니다."),
            () -> assertEquals(expectedResponse.getPrice(), result.getPrice(), "상품 가격이 일치합니다."),
            () -> assertEquals(expectedResponse.getCategory(), result.getCategory(), "상품 카테고리가 일치합니다."),
            () -> assertEquals(expectedResponse.getQuantity(), result.getQuantity(), "상품 수량이 일치합니다."),
            () -> assertEquals(expectedResponse.getProductState(), result.getProductState(), "상품 상태가 일치합니다.")
        );
    }

    @DisplayName("상품을 조회할 때, 상품이 존재하지 않으면 ResourceNotFoundException을 발생시킨다.")
    @Test
    void findProductNotFound() {
        // given
        long productId = 999L;
        when(productRepository.findById(productId)).thenThrow(new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND));
        assertThrows(ResourceNotFoundException.class, () -> {
            productRepository.findById(productId); // 존재하지 않는 상품 ID로 조회
        });
    }
}
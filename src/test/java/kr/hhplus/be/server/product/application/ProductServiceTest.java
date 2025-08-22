package kr.hhplus.be.server.product.application;

import kr.hhplus.be.server.exception.ExceptionCode;
import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO.ProductResult;
import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @DisplayName("재고 차감 후 상품의 재고가 0개면 상품의 상태가 품절 상태로 변경된다.")
    @Test
    void productStateSoldOut(){
        // given
        long firstProductId = 1L;
        long secondProductId = 2L;
        int firstRemainingQuantity = 5;
        int secondRemainingQuantity = 2;
        int firstQuantityToDecrease = 5; // 첫 번째 상품의 재고를 모두 감소
        int secondQuantityToDecrease = 1; // 두 번째 상품의 재고를 모두 감소
        List<Product> products = List.of(
                Product.builder()
                        .productId(firstProductId)
                        .productState(ProductState.AVAILABLE)
                        .name("자바 네트워크 프로그래밍")
                        .description("자바로 배우는 네트워크 프로그래밍의 기초")
                        .price(18000L)
                        .category("IT")
                        .quantity(firstRemainingQuantity)
                        .build(),
                Product.builder()
                        .productId(secondProductId)
                        .productState(ProductState.AVAILABLE)
                        .name("자바 네트워크 프로그래밍")
                        .description("자바로 배우는 네트워크 프로그래밍의 기초")
                        .price(18000L)
                        .category("IT")
                        .quantity(secondRemainingQuantity)
                        .build()
        );

        when(productRepository.findAllById(any(List.class))).thenReturn(products);
        when(productRepository.saveAll(any())).thenReturn(products);

        // when
        List<ProductResult> result = productService.decreaseStock(
                List.of(
                        OrderCommandDTO.CreateOrderItemCommand.builder()
                                .productId(firstProductId)
                                .orderQuantity(firstQuantityToDecrease)
                                .build(),
                        OrderCommandDTO.CreateOrderItemCommand.builder()
                                .productId(secondProductId)
                                .orderQuantity(secondQuantityToDecrease)
                                .build()
                )
        );
        // then
        verify(productRepository).findAllById(any(List.class));
        verify(productRepository).saveAll(any());
        assertEquals(2, result.size(), "두 개의 상품이 반환되어야 합니다.");
        assertThat(result.get(0).getProductId()).isEqualTo(firstProductId);
        assertEquals(firstProductId, result.get(0).getProductId(), "첫 번째 상품 ID가 일치해야 합니다.");
        assertEquals(firstRemainingQuantity - firstQuantityToDecrease, result.get(0).getQuantity());
        assertEquals(ProductState.SOLD_OUT, result.get(0).getProductState(), "첫 번째 상품 상태는 SOLD_OUT이어야 합니다.");
        assertThat(result.get(1).getProductId()).isEqualTo(secondProductId);
        assertEquals(secondProductId, result.get(1).getProductId(), "두 번째 상품 ID가 일치해야 합니다.");
        assertEquals(secondRemainingQuantity - secondQuantityToDecrease, result.get(1).getQuantity());
        assertEquals(ProductState.AVAILABLE, result.get(1).getProductState(), "두 번째 상품 상태는 AVAILABLE이어야 합니다.");


    }
}
//package kr.hhplus.be.server.product.presentation;
//
//import kr.hhplus.be.server.product.common.ProductState;
//import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
//import kr.hhplus.be.server.product.application.ProductRepository;
//import kr.hhplus.be.server.product.infrastructure.repository.rdb.ProductRepositoryImpl;
//import kr.hhplus.be.server.product.mapper.ProductMapper;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
//@AutoConfigureMockMvc
//@RequiredArgsConstructor
//class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//
//    @DisplayName("상품 조회 API 테스트")
//    @Test
//    void getProduct() throws Exception {
//        // given
//        Long productId = 1L;
//
//        productRepository.save(
//                ProductEntity.builder()
//                        .name("클린아키텍처")
//                        .description("클린 아키텍처는 소프트웨어 개발에 있어 중요한 원칙과 패턴을 다루고 있습니다.")
//                        .productState(ProductState.AVAILABLE)
//                        .price(1000L)
//                        .quantity(10)
//                        .category("IT")
//                        .build()
//        );
//
//        // when
//        var mvcResult = mockMvc.perform(get("/products/{productId}", productId)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // then
//        String content = mvcResult.getResponse().getContentAsString();
//        ApiResponse<?> response = objectMapper.readValue(content, ApiResponse.class);
//
//        assertThat(response).isNotNull();
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getData()).isNotNull();
//    }
//}
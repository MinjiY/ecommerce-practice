package kr.hhplus.be.server.product;

import kr.hhplus.be.server.common.CleanUp;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.product.application.ProductServiceImpl;
import kr.hhplus.be.server.product.application.dto.ProductServiceDTO;
import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.infrastructure.repository.ProductJpaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("integration")
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
public class ProductIntegrationTest {

    @Autowired
    private CleanUp cleanUp;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    private Product firstProduct;

    private Product secondProduct;
    private List<OrderCommandDTO.CreateOrderItemCommand> orderedProducts;


    @BeforeEach
    void setUp(){
        long userId = 123L;
        String firstProductName = "First Test Product";
        String secondProductName = "Second Test Product";

        ProductEntity firstProductEntity = productJpaRepository.save(ProductEntity.builder()
                .name(firstProductName)
                .price(1000L)
                        .category("IT")
                        .description("First Test Product Description")
                .productState(ProductState.AVAILABLE)
                .quantity(100)
                .build());

        ProductEntity secondProductEntity = productJpaRepository.save(ProductEntity.builder()
                .name(secondProductName)
                        .category("IT")
                        .description("Second Test Product Description")
                .price(1000L)
                .productState(ProductState.AVAILABLE)
                .quantity(100)
                .build());

        firstProduct = Product.builder()
                .name(firstProductEntity.getName())
                .price(firstProductEntity.getPrice())
                .productId(firstProductEntity.getProductId())
                .category(firstProductEntity.getCategory())
                .description(firstProductEntity.getDescription())
                .productState(firstProductEntity.getProductState())
                .quantity(firstProductEntity.getQuantity())
                .build();

        secondProduct = Product.builder()
                .name(secondProductEntity.getName())
                .price(secondProductEntity.getPrice())
                .productId(secondProductEntity.getProductId())
                .category(secondProductEntity.getCategory())
                .description(secondProductEntity.getDescription())
                .productState(secondProductEntity.getProductState())
                .quantity(secondProductEntity.getQuantity())
                .build();

        orderedProducts = List.of(
                OrderCommandDTO.CreateOrderItemCommand.builder()
                        .userId(userId)
                        .productName(firstProduct.getName())
                        .productAmount(firstProduct.getPrice())
                        .orderQuantity(firstProduct.getQuantity())
                        .productId(firstProduct.getProductId())
                        .build(),
                OrderCommandDTO.CreateOrderItemCommand.builder()
                        .userId(userId)
                        .productName(secondProduct.getName())
                        .productAmount(secondProduct.getPrice())
                        .orderQuantity(secondProduct.getQuantity())
                        .productId(secondProduct.getProductId())
                        .build()
                );
    }

    @AfterEach
    void tearDown() {
        cleanUp.all();
    }

    @DisplayName("상품 재고 차감 통합 테스트")
    @Test
    void decreaseStock() {
        // given
        // when
        List<ProductServiceDTO.ProductResult> result = productServiceImpl.decreaseStock(orderedProducts);

        ProductEntity firstProductEntity = productJpaRepository.findById(firstProduct.getProductId())
                .orElseThrow();
        ProductEntity secondProductEntity = productJpaRepository.findById(secondProduct.getProductId())
                .orElseThrow();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getProductId()).isEqualTo(firstProductEntity.getProductId());
        assertThat(result.get(0).getQuantity()).isEqualTo(firstProductEntity.getQuantity());
        assertThat(result.get(1).getProductId()).isEqualTo(secondProductEntity.getProductId());
        assertThat(result.get(1).getQuantity()).isEqualTo(secondProductEntity.getQuantity());
    }

    @DisplayName("재고 차감 동시성 테스트 - 동일한 상품에 대해 동시에 재고 차감 요청을 보냈을 때, 순차적으로 처리된다.")
    @Test
    void decreaseStockConcurrencyTest() throws InterruptedException{
        // given
        int firstDecreaseQuantity = 5;
        int secondDecreaseQuantity = 3;
        int firstProductExpectedQuantity = firstProduct.getQuantity() - (firstDecreaseQuantity * 10); // thoreadCount = 10
        int secondProductExpectedQuantity = secondProduct.getQuantity() - (secondDecreaseQuantity * 10);// thoreadCount = 10
        List<OrderCommandDTO.CreateOrderItemCommand> concurrentOrders = List.of(
                OrderCommandDTO.CreateOrderItemCommand.builder()
                        .userId(123L)
                        .productName(firstProduct.getName())
                        .productAmount(firstProduct.getPrice())
                        .orderQuantity(firstDecreaseQuantity)
                        .productId(firstProduct.getProductId())
                        .build(),
                OrderCommandDTO.CreateOrderItemCommand.builder()
                        .userId(123L)
                        .productName(secondProduct.getName())
                        .productAmount(secondProduct.getPrice())
                        .orderQuantity(secondDecreaseQuantity)
                        .productId(secondProduct.getProductId())
                        .build()
        );

        // when
        int threadCount = 10;
        runConcurrency(threadCount, () -> productServiceImpl.decreaseStock(concurrentOrders));

        // then
        ProductEntity firstSavedProduct = productJpaRepository.findById(firstProduct.getProductId()).orElseThrow();
        ProductEntity secondSavedProduct = productJpaRepository.findById(secondProduct.getProductId()).orElseThrow();

        assertThat(firstSavedProduct.getQuantity())
                .withFailMessage("첫번째 상품의 재고가 다릅니다. 실행 후: "+ firstSavedProduct.getQuantity() + " 기댓값: "+ firstProductExpectedQuantity).isEqualTo(firstProductExpectedQuantity);
        assertThat(secondSavedProduct.getQuantity())
                .withFailMessage("두번째 상품의 재고가 다릅니다. 실행 후:"+ secondSavedProduct.getQuantity() + " 기댓값: "+ secondProductExpectedQuantity).isEqualTo(secondProductExpectedQuantity);
    }

    private void runConcurrency(int threadCount, Runnable task) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();
    }

}


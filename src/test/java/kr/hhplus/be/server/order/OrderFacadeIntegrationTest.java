package kr.hhplus.be.server.order;


import kr.hhplus.be.server.common.CleanUp;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.CouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.infrastructure.repository.MapUserCouponJpaRepository;
import kr.hhplus.be.server.exception.custom.InsufficientStockException;
import kr.hhplus.be.server.order.application.OrderFacadeService;
import kr.hhplus.be.server.order.application.dto.OrderCommandDTO;
import kr.hhplus.be.server.order.common.OrderStatus;
import kr.hhplus.be.server.order.domain.Order;
import kr.hhplus.be.server.order.domain.OrderItem;
import kr.hhplus.be.server.order.infrastructure.entity.OrderEntity;
import kr.hhplus.be.server.order.infrastructure.entity.OrderItemEntity;
import kr.hhplus.be.server.order.infrastructure.repository.OrderItemJpaRepository;
import kr.hhplus.be.server.order.infrastructure.repository.OrderJpaRepository;
import kr.hhplus.be.server.point.common.TransactionType;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.infrastructure.entity.PointHistoryEntity;
import kr.hhplus.be.server.point.infrastructure.repository.PointHistoryJpaRepository;
import kr.hhplus.be.server.point.infrastructure.repository.PointJpaRepository;
import kr.hhplus.be.server.product.common.ProductState;
import kr.hhplus.be.server.product.domain.Product;
import kr.hhplus.be.server.product.infrastructure.entity.ProductEntity;
import kr.hhplus.be.server.product.infrastructure.repository.ProductJpaRepository;
import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import lombok.Builder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Tag("integration")
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
public class OrderFacadeIntegrationTest {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private CleanUp cleanUp;

    @Autowired
    private PointJpaRepository pointJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private PointHistoryJpaRepository pointHistoryJpaRepository;

    @Autowired
    private OrderItemJpaRepository orderItemJpaRepository;

    @Autowired
    private MapUserCouponJpaRepository mapUserCouponJpaRepository;

    @Autowired
    private CouponJpaRepository couponJpaRepository;


    private Product product;

    private Point point;

    private Order order;

    private PointHistory pointHistory;

    private OrderItem orderItem;

    private Coupon coupon;

    private MapUserCoupon mapUserCoupon;

    @BeforeEach
    void setUp() {
        long userId = 1L;

        ProductEntity productEntity = productJpaRepository.save(
                ProductEntity.builder()
                        .name("맥북 프로")
                        .price(10000L)
                        .description("맥북 프로 16인치")
                        .category("laptop")
                        .quantity(5)
                        .productState(ProductState.AVAILABLE)
                        .build()
                );

        PointEntity pointEntity = pointJpaRepository.save(
                PointEntity.builder()
                        .userId(1L)
                        .balance(100000L)
                        .build()
                );

        PointHistoryEntity pointHistoryEntity = pointHistoryJpaRepository.save(
                    PointHistoryEntity.builder()
                            .pointId(pointEntity.getPointId())
                        .userId(pointEntity.getUserId())
                        .amount(10000L)
                        .transactionType(TransactionType.DEPOSIT)
                        .build()
                );

        OrderEntity orderEntity = orderJpaRepository.save(
                OrderEntity.builder()
                        .userId(userId)
                        .orderStatus(OrderStatus.COMPLETED)
                        .totalAmount(10000L)
                        .discountAmount(0L)
                        .paidAmount(10000L)
                        .build()
                );

        OrderItemEntity orderItemEntity = orderItemJpaRepository.save(
                OrderItemEntity.builder()
                        .order(orderEntity)
                        .userId(userId)
                        .productName(productEntity.getName())
                        .productAmount(productEntity.getPrice())
                        .orderQuantity(2)
                        .productId(productEntity.getProductId())
                        .build()
                );

        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .accounts("account1")
                .build();

        CouponEntity couponEntity = couponJpaRepository.save(CouponEntity.builder()
                .couponName("50% 할인 쿠폰")
                .discountRate(new BigDecimal("0.5"))
                        .expirationDate(LocalDate.of(2025, 12, 31))
                        .issuableQuantity(10)
                        .remainingQuantity(9)
                        .issuedQuantity(1)

                .build());

        MapUserCouponEntity mapUserCouponEntity = mapUserCouponJpaRepository.save(MapUserCouponEntity.builder()
                .userId(userId)
                .couponId(couponEntity.getCouponId())
                .couponState(CouponState.ACTIVE)
                .couponName(couponEntity.getCouponName())
                .build());


        product = Product.builder()
                .productId(productEntity.getProductId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .category(productEntity.getCategory())
                .price(productEntity.getPrice())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .quantity(productEntity.getQuantity())
                .productState(productEntity.getProductState())
                .build();

        point = Point.builder()
                .balance(pointEntity.getBalance())
                .pointId(pointEntity.getPointId())
                .userId(pointEntity.getUserId())
                .build();
        pointHistory = PointHistory.builder()
                .pointId(pointHistoryEntity.getPointId())
                .userId(pointHistoryEntity.getUserId())
                .amount(pointHistoryEntity.getAmount())
                .transactionType(pointHistoryEntity.getTransactionType())
                .build();
        order = Order.builder()
                .orderId(orderEntity.getOrderId())
                .userId(orderEntity.getUserId())
                .orderStatus(orderEntity.getOrderStatus())
                .totalAmount(orderEntity.getTotalAmount())
                .discountAmount(orderEntity.getDiscountAmount())
                .paidAmount(orderEntity.getPaidAmount())
                .build();
        orderItem = OrderItem.builder()
                .userId(orderItemEntity.getUserId())
                .productName(orderItemEntity.getProductName())
                .productAmount(orderItemEntity.getProductAmount())
                .orderQuantity(orderItemEntity.getOrderQuantity())
                .productId(orderItemEntity.getProductId())
                .build();

        coupon = Coupon.builder()
                .couponId(couponEntity.getCouponId())
                .couponName(couponEntity.getCouponName())
                .discountRate(couponEntity.getDiscountRate())
                .expirationDate(couponEntity.getExpirationDate())
                .build();

        mapUserCoupon = MapUserCoupon.builder()
                .userId(mapUserCouponEntity.getUserId())
                .couponId(mapUserCouponEntity.getCouponId())
                .couponState(mapUserCouponEntity.getCouponState())
                .couponName(mapUserCouponEntity.getCouponName())
                .build();
    }

    @AfterEach
    public void tearDown() {
        cleanUp.all();

    }


    @DisplayName("주문 생성 테스트 - 쿠폰 미포함")
    @Test
    void createOrderTestWithOutCoupon() {
        // given
        Long userId = 1L;
        Long productId = product.getProductId();
        Integer orderQuantity = 2;
        Integer initialProductQuantity = product.getQuantity();
        Integer expectedProductQuantity = initialProductQuantity - orderQuantity;
        Long initialUserPoint = point.getBalance();
        Long expectedUserPoint = initialUserPoint - (product.getPrice() * orderQuantity);

        Long totalAmount = product.getPrice() * orderQuantity;

        OrderCommandDTO.CreateOrderCommand command = OrderCommandDTO.CreateOrderCommand.builder()
                .userId(userId)
                .paidAmount(totalAmount)
                .discountAmount(0L)
                .orderedAmount(totalAmount)
                .orderedProducts(
                        List.of(
                        OrderCommandDTO.CreateOrderItemCommand.builder()
                                    .userId(userId)
                                    .productName(product.getName())
                                    .orderQuantity(orderQuantity)
                                    .productAmount(product.getPrice())
                                    .productId(productId)
                                    .build()
                        )
                )
                .build();

        // when
        OrderCommandDTO.CreateOrderResult result = orderFacadeService.createOrder(command);

        // then
        Assertions.assertNotNull(result);
        assertThat(result.getOrderId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getOrderedAmount()).isEqualTo(totalAmount);
        assertThat(result.getDiscountAmount()).isEqualTo(0L);
        assertThat(result.getPaidAmount()).isEqualTo(totalAmount);
        assertThat(result.getOrderedProducts()).hasSize(1);
        assertThat(result.getOrderedProducts().get(0).getUserId()).isEqualTo(userId);
        assertThat(result.getOrderedProducts().get(0).getProductName()).isEqualTo(product.getName());
        assertThat(result.getOrderedProducts().get(0).getOrderQuantity()).isEqualTo(orderQuantity);
        assertThat(result.getOrderedProducts().get(0).getProductAmount()).isEqualTo(product.getPrice());
        assertThat(result.getOrderedProducts().get(0).getProductId()).isEqualTo(productId);

        // 재고
        ProductEntity resultProduct = productJpaRepository.findById(productId).orElseThrow();
        assertThat(resultProduct.getQuantity()).isEqualTo(expectedProductQuantity);

        // 포인트
        PointEntity resultPoint = pointJpaRepository.findById(userId).orElseThrow();
        assertThat(resultPoint.getBalance()).isEqualTo(expectedUserPoint);

        // 주문
        OrderEntity resultOrder = orderJpaRepository.findById(result.getOrderId()).orElseThrow();
        assertThat(resultOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("주문 생성 테스트 - 쿠폰 포함")
    @Test
    void createOrderTestCoupon() {
        // 1. 재고차감 되었는지
        // 2. 포인트 차감 되었는지
        // 3. 쿠폰 할인금액 적용 되었는지
        // 4. 주문 생성 되었는지
        // given
        Long userId = 1L;
        Long productId = product.getProductId();
        Integer orderQuantity = 2;
        Integer initialProductQuantity = product.getQuantity();
        Integer expectedProductQuantity = initialProductQuantity - orderQuantity;
        Long initialUserPoint = point.getBalance();
        Long totalAmount = product.getPrice() * orderQuantity;
        Long couponDiscountAmount = totalAmount * coupon.getDiscountRate().longValue(); // 가정: 쿠폰 할인 금액
        boolean isCouponUsed = true; // 쿠폰 사용 여부
        Long paidAmount = totalAmount - couponDiscountAmount;
        Long expectedUserPoint = initialUserPoint - paidAmount;



        OrderCommandDTO.CreateOrderCommand command = OrderCommandDTO.CreateOrderCommand.builder()
                .userId(userId)
                .paidAmount(paidAmount)
                .discountAmount(couponDiscountAmount)
                .orderedAmount(totalAmount)
                .isCouponUsed(isCouponUsed)
                .couponId(coupon.getCouponId())
                .orderedProducts(
                        List.of(
                                OrderCommandDTO.CreateOrderItemCommand.builder()
                                        .userId(userId)
                                        .productName(product.getName())
                                        .orderQuantity(orderQuantity)
                                        .productAmount(product.getPrice())
                                        .productId(productId)
                                        .build()
                        )
                )
                .build();

        // when
        OrderCommandDTO.CreateOrderResult result = orderFacadeService.createOrder(command);

        // then
        Assertions.assertNotNull(result);
        assertThat(result.getOrderId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getOrderedAmount()).isEqualTo(totalAmount);
        assertThat(result.getDiscountAmount()).isEqualTo(0L);
        assertThat(result.getPaidAmount()).isEqualTo(totalAmount);
        assertThat(result.getOrderedProducts()).hasSize(1);
        assertThat(result.getOrderedProducts().get(0).getUserId()).isEqualTo(userId);
        assertThat(result.getOrderedProducts().get(0).getProductName()).isEqualTo(product.getName());
        assertThat(result.getOrderedProducts().get(0).getOrderQuantity()).isEqualTo(orderQuantity);
        assertThat(result.getOrderedProducts().get(0).getProductAmount()).isEqualTo(product.getPrice());
        assertThat(result.getOrderedProducts().get(0).getProductId()).isEqualTo(productId);

        // 재고
        ProductEntity resultProduct = productJpaRepository.findById(productId).orElseThrow();
        assertThat(resultProduct.getQuantity()).isEqualTo(expectedProductQuantity);

        // 포인트 + 할인여부
        PointEntity resultPoint = pointJpaRepository.findById(userId).orElseThrow();
        assertThat(resultPoint.getBalance()).isEqualTo(expectedUserPoint);

        // 쿠폰 사용 여부
        MapUserCouponEntity resultMapUserCoupon = mapUserCouponJpaRepository.findByUserIdAndCouponId(
                userId, coupon.getCouponId()
            ).orElseThrow();
        assertThat(resultMapUserCoupon.getCouponState()).isEqualTo(CouponState.USED);
        assertThat(resultMapUserCoupon.getCouponId()).isEqualTo(coupon.getCouponId());


        // 주문
        OrderEntity resultOrder = orderJpaRepository.findById(result.getOrderId()).orElseThrow();
        assertThat(resultOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }


    @DisplayName("재고 부족시 주문이 실패한다.")
    @Test
    void createOrderTestWithInsufficientStock() {
        // given
        Long userId = 1L;
        Long productId = product.getProductId();
        Integer orderQuantity = 10000; // 재고보다 많은 수량 주문
        Long totalAmount = product.getPrice() * orderQuantity;

        OrderCommandDTO.CreateOrderCommand command = OrderCommandDTO.CreateOrderCommand.builder()
                .userId(userId)
                .paidAmount(totalAmount)
                .discountAmount(0L)
                .orderedAmount(totalAmount)
                .orderedProducts(
                        List.of(
                                OrderCommandDTO.CreateOrderItemCommand.builder()
                                        .userId(userId)
                                        .productName(product.getName())
                                        .orderQuantity(orderQuantity)
                                        .productAmount(product.getPrice())
                                        .productId(productId)
                                        .build()
                        )
                )
                .build();

        // when, then
        Assertions.assertThrows(InsufficientStockException.class, () -> {
            orderFacadeService.createOrder(command);
        });
    }


}

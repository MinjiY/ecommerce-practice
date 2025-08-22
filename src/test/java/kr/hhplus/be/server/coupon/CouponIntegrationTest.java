package kr.hhplus.be.server.coupon;


import kr.hhplus.be.server.common.CleanUp;
import kr.hhplus.be.server.coupon.application.IssueCouponService;
import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.CouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.repository.rdb.CouponJpaRepository;
import kr.hhplus.be.server.coupon.infrastructure.repository.rdb.MapUserCouponJpaRepository;
import kr.hhplus.be.server.user.domain.User;
import kr.hhplus.be.server.user.infrastructure.entity.UserEntity;
import kr.hhplus.be.server.user.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("integration")
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
public class CouponIntegrationTest {

    @Autowired
    private IssueCouponService issueCouponService;

    @Autowired
    private CleanUp cleanUp;

    @Autowired
    private MapUserCouponJpaRepository mapUserCouponJpaRepository;

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    private User user;

    private Coupon coupon;

    private MapUserCoupon mapUserCoupon;

    private Integer issuableQuantity;
    private Integer issuedQuantity;
    private Integer remainingQuantity;

    @BeforeEach
    void setUp() {
        long userId = 1L;
        issuableQuantity = 3;
        issuedQuantity = 0;
        remainingQuantity = 3;
        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .accounts("account1")
                .build();

        CouponEntity couponEntity = couponJpaRepository.save(CouponEntity.builder()
                .couponName("50% 할인 쿠폰")
                .discountRate(new BigDecimal("0.5"))
                .expirationDate(LocalDate.of(2025, 12, 31))
                .issuableQuantity(issuableQuantity)
                .remainingQuantity(remainingQuantity)
                .issuedQuantity(issuedQuantity)
                .build());


        user = User.builder()
                .userId(userEntity.getUserId())
                .accounts(userEntity.getAccounts())
                .build();

        coupon = Coupon.builder()
                .couponId(couponEntity.getCouponId())
                .couponName(couponEntity.getCouponName())
                .discountRate(couponEntity.getDiscountRate())
                .expirationDate(couponEntity.getExpirationDate())
                .build();

    }

    @AfterEach
    public void tearDown() {
        cleanUp.all();
    }


    @DisplayName("쿠폰 발급 테스트")
    @Test
    void issuedCouponTest(){
        // when
        issueCouponService.issueCoupon(
                CouponCommandDTO.issueCouponCommand.builder()
                        .userId(user.getUserId())
                        .couponId(coupon.getCouponId())
                        .build()
        );

        CouponEntity couponEntity = couponJpaRepository.findById(coupon.getCouponId())
                .orElseThrow(null);
        MapUserCouponEntity savedMapUserCouponEntity = mapUserCouponJpaRepository.findByUserIdAndCouponId(user.getUserId(), coupon.getCouponId())
                .orElseThrow(null);

        assertThat(savedMapUserCouponEntity).isNotNull();
        assertThat(savedMapUserCouponEntity.getUserId()).isEqualTo(user.getUserId());
        assertThat(savedMapUserCouponEntity.getCouponId()).isEqualTo(coupon.getCouponId());
        assertThat(savedMapUserCouponEntity.getCouponState()).isEqualTo(CouponState.ACTIVE);
        assertThat(savedMapUserCouponEntity.getCouponName()).isEqualTo(coupon.getCouponName());

        assertThat(couponEntity).isNotNull();
        assertThat(couponEntity.getCouponId()).isEqualTo(coupon.getCouponId());
        assertThat(couponEntity.getCouponName()).isEqualTo(coupon.getCouponName());
        assertThat(couponEntity.getIssuedQuantity()).isEqualTo(1);
        assertThat(couponEntity.getRemainingQuantity()).isEqualTo(2);
        assertThat(couponEntity.getExpirationDate()).isEqualTo(coupon.getExpirationDate());
    }


    @DisplayName("쿠폰 발급 동시성 테스트 - 여러 유저가 동시에 쿠폰을 발급받는 경우")
    @Test
    void issuedCouponConcurrentTest() throws InterruptedException {

        int threadCount = 3;
        runConcurrent(threadCount, () -> {
            issueCouponService.issueCoupon(
                    CouponCommandDTO.issueCouponCommand.builder()
                            .userId(user.getUserId())
                            .couponId(coupon.getCouponId())
                            .build()

            );
        });


        MapUserCouponEntity getMapUserCouponEntity = mapUserCouponJpaRepository.findByUserIdAndCouponId(user.getUserId(), coupon.getCouponId())
                .orElseThrow(null);
        CouponEntity getCouponEntity = couponJpaRepository.findById(coupon.getCouponId())
                .orElseThrow(null);

        assertThat(getMapUserCouponEntity).isNotNull();
        assertThat(getMapUserCouponEntity.getUserId()).isEqualTo(user.getUserId());
        assertThat(getMapUserCouponEntity.getCouponId()).isEqualTo(coupon.getCouponId());
        assertThat(getMapUserCouponEntity.getCouponState()).isEqualTo(CouponState.ACTIVE);
        assertThat(getMapUserCouponEntity.getCouponName()).isEqualTo(coupon.getCouponName());

        // 발급된 쿠폰의 수량은 스레드를 3개 돌렸지만 1개만 발급되었어야함
        assertThat(getCouponEntity).isNotNull();
        assertThat(getCouponEntity.getIssuedQuantity()).isEqualTo(1);
        assertThat(getCouponEntity.getRemainingQuantity()).isEqualTo(2);
    }

    private void runConcurrent(int threadCount, Runnable task) throws InterruptedException {
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

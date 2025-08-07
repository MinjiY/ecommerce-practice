package kr.hhplus.be.server.coupon;


import kr.hhplus.be.server.common.CleanUp;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.coupon.infrastructure.entity.CouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.entity.MapUserCouponEntity;
import kr.hhplus.be.server.coupon.infrastructure.repository.CouponJpaRepository;
import kr.hhplus.be.server.coupon.infrastructure.repository.MapUserCouponJpaRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("integration")
@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class})
public class CouponIntegrationTest {

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

    @BeforeEach
    void setUp() {
        long userId = 1L;

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


    @DisplayName("쿠폰 발급 테스트")
    @Test
    void issuedCouponTest(){
        // when
        MapUserCouponEntity savedMapUserCouponEntity = mapUserCouponJpaRepository.save(
                MapUserCouponEntity.builder()
                        .userId(user.getUserId())
                        .couponId(coupon.getCouponId())
                        .couponState(CouponState.ACTIVE)
                        .couponName(coupon.getCouponName())
                        .build()
        );
        assertThat(savedMapUserCouponEntity).isNotNull();
        assertThat(savedMapUserCouponEntity.getUserId()).isEqualTo(user.getUserId());
        assertThat(savedMapUserCouponEntity.getCouponId()).isEqualTo(coupon.getCouponId());
        assertThat(savedMapUserCouponEntity.getCouponState()).isEqualTo(CouponState.ACTIVE);
        assertThat(savedMapUserCouponEntity.getCouponName()).isEqualTo(coupon.getCouponName());
    }
}

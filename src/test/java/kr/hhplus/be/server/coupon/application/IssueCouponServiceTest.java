package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.Coupon;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import kr.hhplus.be.server.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueCouponServiceTest {

    @InjectMocks
    private IssueCouponService issueCouponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapUserCouponRepository mapUserCouponRepository;


    @DisplayName("쿠폰 발급시 정상 발급된다.")
    @Test
    void issueCoupon() {
        // given
        Long userId = 1L;
        Long couponId = 1L;

        CouponCommandDTO.issueCouponCommand issueCouponCommand = CouponCommandDTO.issueCouponCommand.builder()
                .userId(userId)
                .couponId(couponId)
                .build();

        CouponCommandDTO.issueCouponResult expectedResult = CouponCommandDTO.issueCouponResult.builder()
                .userId(userId)
                .couponId(couponId)
                .couponName("10% 할인 쿠폰")
                .couponState(CouponState.ACTIVE)
                .build();

        when(userRepository.findByUserId(any(User.class))).thenReturn(User.builder().userId(userId).build());
        when(couponRepository.findByCouponId(any(Coupon.class)))
                .thenReturn(Coupon.builder()
                        .couponId(couponId)
                        .couponName("10% 할인 쿠폰")
                        .discountRate(new BigDecimal("0.10"))
                        .build());

        when(mapUserCouponRepository.save(any(MapUserCoupon.class)))
                .thenReturn(MapUserCoupon.builder()
                        .userId(userId)
                        .couponId(couponId)
                        .couponName("10% 할인 쿠폰")
                        .couponState(CouponState.ACTIVE)
                        .build());

        // when
        CouponCommandDTO.issueCouponResult result = issueCouponService.issueCoupon(issueCouponCommand);

        // then
        assertAll(
            () -> assertNotNull(result),
            () -> assertEquals(expectedResult.getUserId(), result.getUserId()),
            () -> assertEquals(expectedResult.getCouponId(), result.getCouponId()),
            () -> assertEquals(expectedResult.getCouponName(), result.getCouponName()),
            () -> assertEquals(expectedResult.getCouponState(), result.getCouponState())
        );
    }
}
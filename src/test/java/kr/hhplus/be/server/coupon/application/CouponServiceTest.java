package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.common.CouponState;
import kr.hhplus.be.server.coupon.domain.MapUserCoupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.CoreMatchers.is;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @DisplayName("유저의 사용 가능한 쿠폰 목록을 조회한다.")
    @Test
    void getAvailableCoupons() {
        // given
        Long userId = 123L;

        List<MapUserCoupon> expectedCoupons = List.of(
            MapUserCoupon.builder().userId(userId).couponId(1L).couponState(CouponState.ACTIVE).couponName("10% 할인 쿠폰").build(),
            MapUserCoupon.builder().userId(userId).couponId(2L).couponState(CouponState.ACTIVE).couponName("30% 할인 쿠폰").build()
        );

        when(couponRepository.findAvailableCouponsByUserIdAndCouponState(
                any(MapUserCoupon.class)
        )).thenReturn(expectedCoupons);

        // when
        CouponCommandDTO.GetAvailableCouponsResult result = couponService.getAvailableCoupons(userId);

        // then
        verify(couponRepository).findAvailableCouponsByUserIdAndCouponState(
            any(MapUserCoupon.class)
        );
        assertNotNull(result);
        assertThat(result.getUserId(), is(userId));
        assertThat(result.getNumberOfAvailableCoupons(), is(expectedCoupons.size()));
        assertThat(result.getAvailableCoupons().get(0).getCouponId(), is(expectedCoupons.get(0).getCouponId()));
        assertThat(result.getAvailableCoupons().get(0).getCouponName(), is(expectedCoupons.get(0).getCouponName()));
        assertThat(result.getAvailableCoupons().get(1).getCouponId(), is(expectedCoupons.get(1).getCouponId()));
        assertThat(result.getAvailableCoupons().get(1).getCouponName(), is(expectedCoupons.get(1).getCouponName()));
    }
}
package kr.hhplus.be.server.coupon.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.hhplus.be.server.coupon.application.CouponService;
import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "사용 가능한 쿠폰 리스트 조회", description = "유저 ID로 사용 가능한 쿠폰 목록을 조회합니다.")
    @GetMapping("/coupons/available")
    public ResponseEntity<ApiResponse<CouponCommandDTO.GetAvailableCouponsResult>> getAvailableCoupons(
            @Parameter(description = "유저 ID", required = true, example = "123")
            @RequestParam Long userId
    ) {
        CouponCommandDTO.GetAvailableCouponsResult result = couponService.getAvailableCoupons(userId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

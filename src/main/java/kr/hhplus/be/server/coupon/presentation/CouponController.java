package kr.hhplus.be.server.coupon.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.coupon.application.CouponService;
import kr.hhplus.be.server.coupon.application.IssueCoupon;
import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.presentation.dto.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;
    private final IssueCoupon issueCouponService;

    @Operation(summary = "사용 가능한 쿠폰 리스트 조회", description = "유저 ID로 사용 가능한 쿠폰 목록을 조회합니다.")
    @GetMapping("/coupons/available")
    public ResponseEntity<ApiResponse<CouponCommandDTO.GetAvailableCouponsResult>> getAvailableCoupons(
            @Parameter(description = "유저 ID", required = true, example = "123")
            @RequestParam Long userId
    ) {
        CouponCommandDTO.GetAvailableCouponsResult result = couponService.getAvailableCoupons(userId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "쿠폰 취소", description = "유저 ID와 쿠폰 ID로 쿠폰을 취소합니다.")
    @PutMapping("/coupons/{couponId}/cancel")
    public ResponseEntity<ApiResponse<CouponCommandDTO.canceledCouponResult>> cancelCoupon(
            @Parameter(description = "유저 ID", required = true, example = "123")
            @RequestParam Long userId,
            @Parameter(description = "쿠폰 ID", required = true, example = "456")
            @PathVariable Long couponId
    ) {
        CouponCommandDTO.cancelCouponCommand cancelCouponCommand = CouponCommandDTO.cancelCouponCommand.builder()
                .userId(userId)
                .couponId(couponId)
                .build();
        CouponCommandDTO.canceledCouponResult result = couponService.cancelCoupon(cancelCouponCommand);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "쿠폰 발급", description = "유저 ID와 쿠폰 ID로 쿠폰을 발급합니다.")
    @PostMapping("/coupons/issue")
    public ResponseEntity<ApiResponse<CouponCommandDTO.issueCouponResult>> issueCoupon(
            @Valid @RequestBody RequestDTO.IssueCouponRequest request
    ) {
        CouponCommandDTO.issueCouponResult result = issueCouponService.issueCoupon(request.toCommand());
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

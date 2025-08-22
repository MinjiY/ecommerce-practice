package kr.hhplus.be.server.coupon.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.coupon.application.CouponService;
import kr.hhplus.be.server.coupon.application.IssueCoupon;
import kr.hhplus.be.server.coupon.application.IssueCouponService;
import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.presentation.dto.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;
    private final IssueCouponService issueCouponService;

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
            @Valid @RequestBody RequestDTO.cancelCouponRequest request
    ) {
        CouponCommandDTO.canceledCouponResult result = couponService.cancelCoupon(request.toCommand());
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "쿠폰 발급", description = "유저 ID와 쿠폰 ID로 쿠폰을 발급합니다.")
    @PostMapping("/coupons/issue")
    public ResponseEntity<ApiResponse<String>> issueCoupon(
            @Valid @RequestBody RequestDTO.IssueCouponRequest request
    ) {
        issueCouponService.joinWaitingList(request.toCommand());
        return ResponseEntity.ok(ApiResponse
                .success(HttpStatus.SEE_OTHER, "쿠폰 발급 신청이 완료되었습니다. 대기열에서 처리 중입니다.", "쿠폰 발급 신청이 완료되었습니다. 대기열에서 처리 중입니다."));
    }

    @Operation(summary = "쿠폰 발급 후 클라이언트가 Redirect로 발급 결과를 확인하는 API", description = "쿠폰 발급의 결과를 확인합니다.")
    @GetMapping("/coupons/issue")
    public ResponseEntity<ApiResponse<CouponCommandDTO.issueCouponResult>> issueCouponStatus(
            @Valid @RequestBody RequestDTO.IssueCouponRequest request
    ) {

        //CouponCommandDTO.issueCouponResult result = issueCouponService.issueCoupon(request.toCommand());
        return ResponseEntity.ok(ApiResponse
                .success(HttpStatus.SEE_OTHER, "쿠폰 발급 신청이 완료되었습니다. 대기열에서 처리 중입니다."));
    }


    @Operation(summary = "쿠폰 사용", description = "유저 ID와 쿠폰 ID로 쿠폰을 사용합니다.")
    @PutMapping("/coupons/{couponId}/user")
    public ResponseEntity<ApiResponse<CouponCommandDTO.useCouponResult>> cancelCoupon(
            @Valid @RequestBody RequestDTO.useCouponRequest request
    ) {
        CouponCommandDTO.useCouponResult result = couponService.useCoupon(request.toCommand());
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

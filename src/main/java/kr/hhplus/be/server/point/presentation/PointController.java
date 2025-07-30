package kr.hhplus.be.server.point.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import kr.hhplus.be.server.point.application.PointService;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.presentation.dto.RequestChargeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PointController {

    private final PointService pointService;

    @Operation(summary = "유저 결제 금액 충전", description = "요청 정보를 받아 결제 금액을 충전합니다.")
    @PostMapping("/points")
    public ResponseEntity<ApiResponse<PointCommandDTO.ChargePointResult>> chargePoint(
            @Valid @RequestBody RequestChargeDTO requestChargePoint
    ) {
        PointCommandDTO.ChargePointResult chargePointResult = pointService.chargePoint(requestChargePoint.toCommand());
        return ResponseEntity.ok(
                ApiResponse.success(
                        chargePointResult
                )
        );
    }

    // userId를 token에서 꺼내와서 사용해야 하지만 로그인은 없고 point 하위의 리소스는 아니기 때문에 requestParam으로 받습니다.
    @Operation(summary = "유저의 결제 금액 조회", description = "userId로 결제 금액을 조회합니다.")
    @GetMapping("/points")
    public ResponseEntity<ApiResponse<PointCommandDTO.GetUserPointResult>> getPoint(
            @Parameter(description = "유저 ID", required = true, example = "123") @RequestParam Long userId
    ) {
        return ResponseEntity.ok(ApiResponse.success(pointService.getUserPoint(userId)));
    }
}

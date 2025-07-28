package kr.hhplus.be.server.point.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.hhplus.be.server.point.application.PointService;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.presentation.dto.RequestChargeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

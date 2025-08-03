package kr.hhplus.be.server.point.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.mapper.PointMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestChargeDTO {

    @NotNull(message = "유저 ID는 필수입니다.")
    @Schema(description = "유저 ID", example = "1234")
    private Long userId;

    @NotNull(message = "충전 금액은 필수입니다.")
    @Schema(description = "충전 금액", example = "1000")
    private Long amount;

    public PointCommandDTO.chargePointCommand toCommand(){
        return PointMapper.INSTANCE.toChargePointCommand(this);
    }
}

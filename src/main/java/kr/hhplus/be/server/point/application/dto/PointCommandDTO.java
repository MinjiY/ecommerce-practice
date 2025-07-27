package kr.hhplus.be.server.point.application.dto;

import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.mapper.PointMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PointCommandDTO {

    // amount를 음수로 요청하는 것은 RequestBody에서 validation 처리한다.
    @Builder
    public static class chargePointCommand {
        private Long userId;
        private Long amount;

        public Point toDomain(){
            return PointMapper.INSTANCE.dtoToDomain(this);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class chargePointResult {
        private Long pointId;
        private Long balance;
        private Long userId;

        public static chargePointResult from(Point point) {
            return chargePointResult.builder()
                    .pointId(point.getPointId())
                    .balance(point.getBalance())
                    .userId(point.getUserId())
                    .build();
        }
    }

}

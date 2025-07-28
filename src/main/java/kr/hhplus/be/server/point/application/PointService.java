package kr.hhplus.be.server.point.application;


import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.common.TransactionType;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private PointRepository pointRepository;
    private PointHistoryRepository pointHistoryRepository;

    public PointCommandDTO.ChargePointResult chargePoint(PointCommandDTO.chargePointCommand chargePointCommand) {
        // 요청 유저에 대한 data row 가 없으면 새로 생성
        Point userPoint = pointRepository.findByUserId(chargePointCommand.getUserId());
        userPoint.chargeAmount(chargePointCommand.getAmount());
        Point savedPoint = pointRepository.save(userPoint);
        pointHistoryRepository.save(
                PointHistory.builder()
                        .pointId(savedPoint.getPointId())
                        .userId(savedPoint.getUserId())
                        .amount(chargePointCommand.getAmount())
                        .transactionType(TransactionType.DEPOSIT)
                        .build()
        );
        return PointCommandDTO.ChargePointResult.from(savedPoint);
    }


}

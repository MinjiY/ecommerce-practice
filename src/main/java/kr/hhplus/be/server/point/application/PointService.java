package kr.hhplus.be.server.point.application;


import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.common.TransactionType;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

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

    public PointCommandDTO.GetUserPointResult getUserPoint(Long userId){
        final long findUserId = userId;
        Point userPoint = pointRepository.findByUserId(findUserId);
        return PointCommandDTO.GetUserPointResult.from(userPoint);
    }

    public PointCommandDTO.WithDrawPointResult withdrawPoint(PointCommandDTO.withdrawPointCommand withdrawPointCommand) {
        // Point 테이블의 유저의 row가 없으면 0포인트로 간주
        Point userPoint = pointRepository.findByUserId(withdrawPointCommand.getUserId());
        userPoint.withdrawAmount(withdrawPointCommand.getAmount());
        Point savedPoint = pointRepository.save(userPoint);
        pointHistoryRepository.save(
                PointHistory.builder()
                        .pointId(savedPoint.getPointId())
                        .userId(savedPoint.getUserId())
                        .amount(withdrawPointCommand.getAmount())
                        .transactionType(TransactionType.WITHDRAW)
                        .build()
        );
        return PointCommandDTO.WithDrawPointResult.from(savedPoint);
    }


}

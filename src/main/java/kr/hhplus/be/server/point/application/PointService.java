package kr.hhplus.be.server.point.application;


import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private PointRepository pointRepository;

    public PointCommandDTO.chargePointResult chargePoint(PointCommandDTO.chargePointCommand chargePointCommand) {
        // 요청 유저에 대한 data row 가 없으면 새로 생성
        Point userPoint = pointRepository.findByUserId(chargePointCommand.getUserId());
        userPoint.chargeAmount(chargePointCommand.getAmount());
        return PointCommandDTO.chargePointResult.from(pointRepository.save(userPoint));
    }


}

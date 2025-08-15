package kr.hhplus.be.server.point.application;

import kr.hhplus.be.server.point.application.dto.PointCommandDTO;

public interface PointService {

    PointCommandDTO.ChargePointResult chargePoint(PointCommandDTO.chargePointCommand chargePointCommand);

    PointCommandDTO.GetUserPointResult getUserPoint(Long userId);

    PointCommandDTO.WithDrawPointResult withdrawPoint(PointCommandDTO.withdrawPointCommand withdrawPointCommand);
}

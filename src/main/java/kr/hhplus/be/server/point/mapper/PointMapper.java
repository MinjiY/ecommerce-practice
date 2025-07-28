package kr.hhplus.be.server.point.mapper;


import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.infrastructure.entity.PointHistoryEntity;
import kr.hhplus.be.server.point.presentation.dto.RequestChargeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointMapper {

    PointMapper INSTANCE = Mappers.getMapper(PointMapper.class);

    Point entityDomain(PointEntity pointEntity);
    PointEntity domainToEntity(Point point);

    Point dtoToDomain(PointCommandDTO.chargePointCommand chargePointCommand);


    PointCommandDTO.chargePointCommand toChargePointCommand(RequestChargeDTO requestChargeDTO);



    PointHistory entityToDomain(PointHistoryEntity pointHistoryEntity);
    PointHistoryEntity domainToEntity(PointHistory pointHistory);
}

package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.point.application.PointRepository;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.mapper.PointMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointRepository;

    private final PointMapper pointMapper;

    @Override
    public Point save(Point point){
        PointEntity pointEntity = pointMapper.domainToEntity(point);
        return pointMapper.entityDomain(pointRepository.save(pointEntity));
    }

}

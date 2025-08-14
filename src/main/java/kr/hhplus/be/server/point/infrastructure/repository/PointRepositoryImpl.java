package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.point.application.PointRepository;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.mapper.PointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointRepository;

    @Override
    public Point save(Point point){
        PointEntity pointEntity = PointMapper.INSTANCE.domainToEntity(point);
        pointEntity.setPointId(point.getPointId());
        return PointMapper.INSTANCE.entityDomain(pointRepository.save(pointEntity));
    }

    @Override
    public Point findByUserId(Long userId){
        PointEntity pointEntity = pointRepository.findByUserId(userId)
                .orElse(new PointEntity(0L, userId));
        return PointMapper.INSTANCE.entityDomain(pointEntity);
    }

}

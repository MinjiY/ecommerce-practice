package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.point.application.PointHistoryRepository;
import kr.hhplus.be.server.point.domain.PointHistory;
import kr.hhplus.be.server.point.infrastructure.entity.PointHistoryEntity;
import kr.hhplus.be.server.point.mapper.PointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final JpaRepository<PointHistoryEntity, Long> pointHistoryJpaRepository;

    private PointMapper pointMapper;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return pointMapper.entityToDomain(pointHistoryJpaRepository.save(pointMapper.domainToEntity(pointHistory)));
    }
}

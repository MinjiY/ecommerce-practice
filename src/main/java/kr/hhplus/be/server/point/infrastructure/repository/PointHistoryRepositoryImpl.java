package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.point.application.PointHistoryRepository;
import kr.hhplus.be.server.point.domain.PointHistory;
import kr.hhplus.be.server.point.infrastructure.entity.PointHistoryEntity;
import kr.hhplus.be.server.point.mapper.PointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryJpaRepository pointHistoryRepository;

    @Override
    public PointHistory save(PointHistory pointHistory) {
        return PointMapper.INSTANCE.entityToDomain(pointHistoryRepository.save(PointMapper.INSTANCE.domainToEntity(pointHistory)));
    }
}

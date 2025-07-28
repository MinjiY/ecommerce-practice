package kr.hhplus.be.server.point.infrastructure.repository;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.point.application.PointRepository;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class PointRepositoryImplTest {

    @Autowired
    private PointJpaRepository pointJpaRepository;

    @DisplayName("Point 테이블에서 UserId로 포인트를 조회한다.")
    @Test
    void findByUserId() {
         // given
         Long userId = 1L;
         PointEntity expectedPoint = pointJpaRepository.save(new PointEntity(1000L, userId));

         // when
        PointEntity actualPoint = pointJpaRepository.findByUserId(userId)
                .orElseThrow(ResourceNotFoundException::new);

        //then
        assertNotNull(actualPoint);
        assertEquals(expectedPoint.getUserId(), actualPoint.getUserId());
        assertEquals(expectedPoint.getBalance(), actualPoint.getBalance());
    }

    @DisplayName("Point 테이블에 충전시 기존 잔액에 충전 요청 금액을 더한 값을 반환해야한다.")
    @Test
    void accumulatePoint(){
        // given
        long userId = 123L;
        long initialBalance = 1000L;
        long chargeAmount = 500L;
        long expectedBalance = initialBalance + chargeAmount;

        PointEntity initialize = pointJpaRepository.save(new PointEntity(initialBalance, userId));

        // when
        PointEntity pointEntity = pointJpaRepository.findByUserId(userId).orElse(
                new PointEntity(0L, userId)
        );
        pointEntity.chargeAmount(chargeAmount);
        PointEntity result = pointJpaRepository.save(pointEntity);

        // then
        assertNotNull(result);
        assertEquals(expectedBalance, result.getBalance());
        assertEquals(userId, result.getUserId());
    }


}
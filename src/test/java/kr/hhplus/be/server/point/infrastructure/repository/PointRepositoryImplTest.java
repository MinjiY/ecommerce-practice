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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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


    @DisplayName("포인트 충전을 위해 UserId로 조회시, 해당 유저의 포인트가 없으면 새로 생성한다.")
    @Test
    void findByUserIdNewRow(){
        // given
        long userId = 123L;
        long chargeAmount = 500L;


        // when
        PointEntity pointEntity = pointJpaRepository.findByUserId(userId).orElse(
                new PointEntity(0L, userId)
        );
        pointEntity.chargeAmount(chargeAmount);
        PointEntity result = pointJpaRepository.save(pointEntity);

        // then
        assertNotNull(result);
        assertThat(chargeAmount).isEqualTo(result.getBalance());
        assertThat(userId).isEqualTo(result.getUserId());
        assertThat(pointJpaRepository.count()).isEqualTo(1L);
        assertThat(pointJpaRepository.findByUserId(userId).isPresent()).isTrue();
        assertThat(pointJpaRepository.findByUserId(userId).get().getBalance()).isEqualTo(chargeAmount);
    }

}
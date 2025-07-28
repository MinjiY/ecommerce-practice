package kr.hhplus.be.server.point.application;

import kr.hhplus.be.server.exception.custom.ResourceNotFoundException;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import kr.hhplus.be.server.point.infrastructure.entity.PointEntity;
import kr.hhplus.be.server.point.infrastructure.repository.PointJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @DisplayName("포인트 충전 요청에 대한 결과를 반환한다.")
    @Test
    void chargePoint() {
        // given
        long userId = 123L;
        long amount = 3000L;

        PointCommandDTO.chargePointCommand command = PointCommandDTO.chargePointCommand.builder()
                .userId(userId)
                .amount(amount)
                .build();

        Point expectedPoint = Point.builder()
                .userId(userId)
                .balance(amount)
                .build();

        // when
        when(pointRepository.save(any(Point.class))).thenReturn(expectedPoint);

        PointCommandDTO.chargePointResult result = pointService.chargePoint(command);

        // then
        assertNotNull(result);
        assertEquals(expectedPoint.getUserId(), result.getUserId());
        assertEquals(expectedPoint.getBalance(), result.getBalance());
    }

    @DisplayName("포인트 충전시 기존 잔액에 충전 요청 금액을 더한 값을 반환해야한다.")
    @Test
    void accumulatePoint(){
        // given
        long userId = 123L;
        long initialBalance = 1000L;
        long chargeAmount = 500L;
        long expectedBalance = initialBalance + chargeAmount;

        Point initialPoint = Point.builder()
                .userId(userId)
                .balance(initialBalance)
                .build();

        when(pointRepository.findByUserId(userId)).thenReturn(initialPoint);
        when(pointRepository.save(any(Point.class))).thenAnswer(invocation -> {
            Point savedDomain = invocation.getArgument(0);
            return savedDomain;
        });

        // when
        PointCommandDTO.chargePointCommand command = PointCommandDTO.chargePointCommand.builder()
                .userId(userId)
                .amount(chargeAmount)
                .build();
        PointCommandDTO.chargePointResult result = pointService.chargePoint(command);

        // then
        verify(pointRepository).findByUserId(userId);
        verify(pointRepository).save(any(Point.class));
        assertNotNull(result);
        assertThat(result.getBalance()).isEqualTo(expectedBalance);
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @DisplayName("포인트 충전시 충전 내역을 기록해야한다.")
    @Test
    void recordPointHistory() {
        // given
        long userId = 123L;
        long chargeAmount = 500L;
        long expectedBalance = 500L;

        PointCommandDTO.chargePointCommand command = PointCommandDTO.chargePointCommand.builder()
                .userId(userId)
                .amount(chargeAmount)
                .build();

        Point initialPoint = Point.builder()
                .userId(userId)
                .balance(0L)
                .build();

        Point expectedPoint = Point.builder()
                .userId(userId)
                .balance(expectedBalance)
                .build();

        when(pointRepository.findByUserId(userId)).thenReturn(initialPoint);
        when(pointRepository.save(any(Point.class))).thenReturn(expectedPoint);

        // when
        pointService.chargePoint(command);

        // then
        verify(pointRepository).findByUserId(userId);
        verify(pointRepository).save(any(Point.class));
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }


}
package kr.hhplus.be.server.point.application;

import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import kr.hhplus.be.server.point.domain.PointHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

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
        PointCommandDTO.ChargePointResult result = pointService.chargePoint(command);

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

    @DisplayName("사용자의 포인트 정보를 조회한다.")
    @Test
    void getUserPoint() {
        // given
        long userId = 123L;
        Point expectedPoint = Point.builder()
                .userId(userId)
                .balance(1000L)
                .build();

        when(pointRepository.findByUserId(userId)).thenAnswer(invocationOnMock -> {
            Long findUserId = invocationOnMock.getArgument(0);
            if (findUserId.equals(expectedPoint.getUserId())) {
                return expectedPoint;
            }
            return Point.builder().userId(findUserId).balance(0L).build();
        });

        // when
        PointCommandDTO.GetUserPointResult result = pointService.getUserPoint(userId);

        // then
        assertNotNull(result);
        assertEquals(expectedPoint.getUserId(), result.getUserId());
        assertEquals(expectedPoint.getBalance(), result.getBalance());
    }

    @DisplayName("포인트 사용 요청에 amount 만큼 차감된 포인트를 반환해야한다.")
    @Test
    void withdrawPoint() {
        // given
        long userId = 123L;
        long withdrawAmount = 500L;

        PointCommandDTO.withdrawPointCommand command = PointCommandDTO.withdrawPointCommand.builder()
                .userId(userId)
                .amount(withdrawAmount)
                .build();

        Point initialPoint = Point.builder()
                .userId(userId)
                .balance(1000L)
                .build();

        Point expectedPoint = Point.builder()
                .userId(userId)
                .balance(500L)
                .build();

        when(pointRepository.findByUserId(userId)).thenReturn(initialPoint);
        when(pointRepository.save(any(Point.class))).thenReturn(expectedPoint);

        // when
        PointCommandDTO.WithDrawPointResult result = pointService.withdrawPoint(command);

        // then
        verify(pointRepository).findByUserId(userId);
        verify(pointRepository).save(any(Point.class));
        verify(pointHistoryRepository).save(any(PointHistory.class));
        assertNotNull(result);
        assertThat(result.getBalance()).isEqualTo(expectedPoint.getBalance());
        assertThat(result.getUserId()).isEqualTo(expectedPoint.getUserId());
    }
}
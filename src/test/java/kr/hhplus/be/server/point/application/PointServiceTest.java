package kr.hhplus.be.server.point.application;

import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import kr.hhplus.be.server.point.domain.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks
    private PointService pointService;

    @Mock
    private PointRepository pointRepository;

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



}
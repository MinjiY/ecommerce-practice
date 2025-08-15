package kr.hhplus.be.server.point.application;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Qualifier;
import kr.hhplus.be.server.point.application.dto.PointCommandDTO;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Primary
@Service
public class PointProxy implements PointService {

    private final PointService pointService;
    private final RedissonClient redissonClient;

    // TODO: Enum으로 묶기
    private final String USE_POINT_PREFIX = "point:use:";
    private final String CHARGE_POINT_PREFIX = "point:charge:";

    public PointProxy(@Qualifier("PointServiceImpl") PointService pointService, RedissonClient redissonClient) {
        this.pointService = pointService;
        this.redissonClient = redissonClient;
    }


    @Override
    public PointCommandDTO.ChargePointResult chargePoint(PointCommandDTO.chargePointCommand chargePointCommand) {
        RLock lock = getLockForPoint(CHARGE_POINT_PREFIX, chargePointCommand.getUserId());
        try {
            if(!lock.tryLock(500, 500, TimeUnit.MICROSECONDS)) {
                throw new IllegalStateException("포인트 충전이 이미 진행 중입니다.");
            }
            return pointService.chargePoint(chargePointCommand);
        } catch (InterruptedException e) {
            throw new RuntimeException("충전 실패");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PointCommandDTO.GetUserPointResult getUserPoint(Long userId) {
        return pointService.getUserPoint(userId);
    }

    @Override
    public PointCommandDTO.WithDrawPointResult withdrawPoint(PointCommandDTO.withdrawPointCommand withdrawPointCommand) {
        RLock lock = getLockForPoint(USE_POINT_PREFIX, withdrawPointCommand.getUserId());
        try {
            if(!lock.tryLock(500, 500, TimeUnit.MICROSECONDS)) {
                throw new IllegalStateException("포인트 사용 처리가 이미 진행 중입니다.");
            }
            return pointService.withdrawPoint(withdrawPointCommand);
        } catch (InterruptedException e) {
            throw new RuntimeException("사용 실패");
        } finally {
            lock.unlock();
        }
    }

    private RLock getLockForPoint(String key, Long userId) {
        String lockKey = key + userId;
        return redissonClient.getLock(lockKey);
    }

}

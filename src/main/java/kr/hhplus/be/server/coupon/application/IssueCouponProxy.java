package kr.hhplus.be.server.coupon.application;

import kr.hhplus.be.server.config.jpa.RedisKeys;
import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
import kr.hhplus.be.server.coupon.infrastructure.repository.rdb.CouponRepositoryImpl;
import kr.hhplus.be.server.exception.custom.CouponIssuanceLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class IssueCouponProxy implements IssueCoupon {

    private final IssueCoupon issueCoupon;
    private final CouponRedisRepository couponRedisRepository;
    private final CouponRepository couponRepository;

    public IssueCouponProxy(
            @Qualifier("issueCouponService") IssueCoupon issueCoupon,
            CouponRedisRepository couponRedisRepository,
            CouponRepositoryImpl couponRepositoryImpl
    ) {
        this.issueCoupon = issueCoupon;
        this.couponRedisRepository = couponRedisRepository;
        this.couponRepository = couponRepositoryImpl;
    }

    @Override
    public CouponCommandDTO.issueCouponResult issueCoupon(CouponCommandDTO.issueCouponCommand issueCouponCommand) {


        try {
            boolean enqueued = queue.offer(issueCouponCommand, 2, TimeUnit.SECONDS);
            if (!enqueued) {
                throw new CouponIssuanceLimitExceededException("선착순 마감(대기열 가득참)");
            }

        //    if (lock.tryLock(10, 1, TimeUnit.SECONDS)) {
                try {
                    CouponCommandDTO.issueCouponCommand commandQueue = queue.poll(1, TimeUnit.SECONDS);
                    if(commandQueue != null){
                        issueCoupon(commandQueue);
                    }
                } finally {
        //            lock.unlock();
                }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("쿠폰 발급 중 오류가 발생했습니다.", e);
        }
        return issueCoupon.issueCoupon(issueCouponCommand);
    }

}

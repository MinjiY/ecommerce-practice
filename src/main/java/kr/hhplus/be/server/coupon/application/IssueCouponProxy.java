//package kr.hhplus.be.server.coupon.application;
//
//import kr.hhplus.be.server.coupon.application.dto.CouponCommandDTO;
//import kr.hhplus.be.server.coupon.infrastructure.repository.CouponJpaRepository;
//import kr.hhplus.be.server.exception.custom.CouponIssuanceLimitExceededException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RBoundedBlockingQueue;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.util.List;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Service
//@Primary
//@Slf4j
//public class IssueCouponProxy implements IssueCoupon {
//
//    private final IssueCoupon issueCoupon;
//    private final RedissonClient redissonClient;
//
//    public IssueCouponProxy(
//            @Qualifier("issueCouponService") IssueCoupon issueCoupon,
//            RedissonClient redissonClient
//            //CouponJpaRepository couponJpaRepository
//    ) {
//        this.issueCoupon = issueCoupon;
//        this.redissonClient = redissonClient;
//        //this.couponJpaRepository = couponJpaRepository;
//    }
//    /*
//        admin
//     */
//    //private final CouponJpaRepository couponJpaRepository; // TODO: admin -> IssueCouponProxy에서 빼고 admin api로 옮겨야함
//
//    private AtomicInteger queueingNumber = new AtomicInteger(0); // 대기열 순번 관리용
//
//    @Value("${io.admin.fcfs}")
//    private Integer fcfs; // TODO: 선착순 몇명에게 발급할지는 Coupon 테이블에서 issuable 수량으로 admin이 admin용 api를 통해 설정한다.
//
//    @Override
//    public CouponCommandDTO.issueCouponResult issueCoupon(CouponCommandDTO.issueCouponCommand issueCouponCommand) {
//        RBoundedBlockingQueue<CouponCommandDTO.issueCouponCommand> queue = getQueueForCoupon(issueCouponCommand.getCouponId());
//        RLock lock = getLockForCoupon(issueCouponCommand.getCouponId());
//        try {
//            // 락안에 대기열? 대기열 먼저하고 순차처리? => 일단 전부 대기열에 넣기
//            boolean enqueued = queue.offer(issueCouponCommand, 2, TimeUnit.SECONDS);
//            if (!enqueued) {
//                throw new CouponIssuanceLimitExceededException("선착순 마감(대기열 가득참)");
//            }
//
//            if (lock.tryLock(10, 1, TimeUnit.SECONDS)) {
//                try {
//                    CouponCommandDTO.issueCouponCommand commandQueue = queue.poll(1, TimeUnit.SECONDS);
//                    if(commandQueue != null){
//                        issueCoupon(commandQueue);
//                    }
//                } finally {
//                    lock.unlock();
//                }
//            } else {
//                throw new RuntimeException("락 획득 실패");
//            }
//        }
//        catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("쿠폰 발급 중 오류가 발생했습니다.", e);
//        }
//        return issueCoupon.issueCoupon(issueCouponCommand);
//    }
//
//    private RBoundedBlockingQueue<CouponCommandDTO.issueCouponCommand> getQueueForCoupon(Long couponId) {
//        String queueKey = "coupon:queue:" + couponId;
//        RBoundedBlockingQueue<CouponCommandDTO.issueCouponCommand> queue = redissonClient.getBoundedBlockingQueue(queueKey);
//        // 이미 용량이 설정되어 있으면 false가 반환되므로, 매번 호출해도 안전
//        queue.trySetCapacity(fcfs);
//        return queue;
//    }
//
//    private RLock getLockForCoupon(Long couponId) {
//        String lockKey = "coupon:lock:" + couponId;
//        return redissonClient.getLock(lockKey);
//    }
//
//}

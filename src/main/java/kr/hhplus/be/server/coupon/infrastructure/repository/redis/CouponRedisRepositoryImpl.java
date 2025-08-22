package kr.hhplus.be.server.coupon.infrastructure.repository.redis;

import kr.hhplus.be.server.config.jpa.RedisKeys;
import kr.hhplus.be.server.coupon.application.CouponRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CouponRedisRepositoryImpl implements CouponRedisRepository {

    private final CouponRedisTemplateRepository couponRedisTemplateRepository;

    @Override
    public Long joinWaitingList(String key, Long couponId, Long userId, Double score) {
        // 중복 발급 신청 방지
        return couponRedisTemplateRepository.joinUserZAdd(key, couponId, userId);
    }
    public Long setAllIssuableCoupons(Long couponId, long quantity) {
        Long result = couponRedisTemplateRepository.incrementHash(RedisKeys.getIssuableCouponIssue(couponId), "quantity", -quantity);
        if(result <= 0){ // 발급 가능 수량이 현재 대기열의 순번보다 작거나 같으면 끝
            return result == 0L ? 0 : result * -1;
        }
        return 0L;
    }

    @Override
    public List<Long> getAllIssuableCouponIds(){
        return couponRedisTemplateRepository.getAllMembers(RedisKeys.getIssuableCouponIds());
    }

    @Override
    public void deleteIssuableCouponIssue(Long couponId) {
        boolean result = couponRedisTemplateRepository.deleteZSetKey(RedisKeys.getIssuableCouponIssue(couponId), couponId);
        if (result) {
            log.info("쿠폰 발급 가능 수량 삭제 성공: couponId={}", couponId);
        }
    }
}

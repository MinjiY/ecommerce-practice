package kr.hhplus.be.server.coupon.application;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CouponRedisRepository {
    Long joinWaitingList(String key, Long couponId, Long userId, Double score);
    List<Long> getAllIssuableCouponIds();
    Long setAllIssuableCoupons(Long couponId, long quantity);
    void deleteIssuableCouponIssue(Long couponId);
}

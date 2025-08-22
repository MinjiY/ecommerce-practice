package kr.hhplus.be.server.coupon.infrastructure.repository.redis;

import java.util.List;

public interface CouponRedisTemplateRepository {
    <T> Double addToSortedSet(String key, T member, Double score);
    long joinUserZAdd(String key, Long couponId, long userId);
    void setHash(String key, long quantity);
    void getHash(String key, String field, long quantity);
    Long incrementHash(String key, String field, long delta);
    List<Long> getAllMembers(String key);
    boolean deleteZSetKey(String key, Long couponId);
}

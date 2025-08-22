package kr.hhplus.be.server.order.infrastructure.repository.redis;

import java.time.Duration;

public interface OrderRedisTemplateRepository {
    <T> Double addToSortedSet(String key, T member, Double score);

    boolean expireIfNoTtl(String key, Duration ttl);
}
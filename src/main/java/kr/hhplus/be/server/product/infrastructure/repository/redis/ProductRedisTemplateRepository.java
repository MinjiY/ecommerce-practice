package kr.hhplus.be.server.product.infrastructure.repository.redis;

import kr.hhplus.be.server.product.application.dto.RankProductDTO;
import kr.hhplus.be.server.product.domain.TopNProductId;

import java.time.Duration;
import java.util.List;

public interface ProductRedisTemplateRepository {
    Long unionAndStore(List<String> keys, String dest, int n);
    boolean expireIfNoTtl(String key, Duration ttl);
    List<TopNProductId> getRankProductsTopN(String key, int limit);
}

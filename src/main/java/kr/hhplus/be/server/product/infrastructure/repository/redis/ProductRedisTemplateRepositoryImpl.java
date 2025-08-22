package kr.hhplus.be.server.product.infrastructure.repository.redis;

import kr.hhplus.be.server.product.application.dto.RankProductDTO;
import kr.hhplus.be.server.product.domain.TopNProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductRedisTemplateRepositoryImpl implements ProductRedisTemplateRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    private static final String EXPIRE_NX_LUA = """
        return redis.call('EXPIRE', KEYS[1], ARGV[1], 'NX')
        """;
    private final DefaultRedisScript<Long> expireNxScript =
            new DefaultRedisScript<>(EXPIRE_NX_LUA, Long.class);


    // keys, dest, keys
    @Override
    public Long unionAndStore(List<String> keys, String dest, int n) {
        // keys 리스트에서 첫 번째 키는 source, 나머지는 union 대상
        List<String> unionKeys = keys.subList(1, n + 1);
        return redisTemplate.opsForZSet().unionAndStore( //default Sum
                keys.get(0),
                unionKeys,
                dest);
    }

    @Override
    public boolean expireIfNoTtl(String key, Duration ttl) {
        Long r = stringRedisTemplate.execute(expireNxScript, List.of(key), String.valueOf(ttl.toSeconds()));
        return r != null && r == 1L;
    }

    @Override
    public List<TopNProductId> getRankProductsTopN(String key, int limit) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, limit - 1).stream()
                .map(entry -> TopNProductId.builder()
                        .productId(Long.valueOf(entry.getValue().toString()))
                        .totalSold(entry.getScore() == null ? 0.0 : entry.getScore())
                        .build()).toList();
    }

}

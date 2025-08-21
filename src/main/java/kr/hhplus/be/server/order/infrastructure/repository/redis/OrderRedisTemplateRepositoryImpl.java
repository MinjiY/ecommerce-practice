package kr.hhplus.be.server.order.infrastructure.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.Entry;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderRedisTemplateRepositoryImpl implements OrderRedisTemplateRepository {


    private final RedisTemplate<String, Object> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    private static final String EXPIRE_NX_LUA = """
        return redis.call('EXPIRE', KEYS[1], ARGV[1], 'NX')
        """;
    private final DefaultRedisScript<Long> expireNxScript =
            new DefaultRedisScript<>(EXPIRE_NX_LUA, Long.class);


    @Override
    public <T> Double addToSortedSet(String key, T member, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, member, score);
    }

    @Override
    public boolean expireIfNoTtl(String key, Duration ttl) {
        Long r = stringRedisTemplate.execute(expireNxScript, List.of(key), String.valueOf(ttl.toSeconds()));
        return r != null && r == 1L;
    }
}

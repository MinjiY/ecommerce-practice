package kr.hhplus.be.server.coupon.infrastructure.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.exception.custom.CouponAlreadyIssuedException;
import kr.hhplus.be.server.exception.custom.CouponIssuanceLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CouponRedisTemplateRepositoryImpl implements CouponRedisTemplateRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper;

    private static final DefaultRedisScript<Long> JOIN_STRICT_SCRIPT;
    static {
        JOIN_STRICT_SCRIPT = new DefaultRedisScript<>();
        JOIN_STRICT_SCRIPT.setResultType(Long.class);
        JOIN_STRICT_SCRIPT.setScriptText("""
            local exists = redis.call('ZSCORE', KEYS[1], ARGV[1])
            if exists then
              return -1
            end
            local seq = redis.call('INCR', KEYS[2])
            redis.call('ZADD', KEYS[1], 'NX', seq, ARGV[1])
            return seq
        """);
    }
    @Override
    public <T> Double addToSortedSet(String key, T member, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, member, score);
    }

    @Override
    public long joinUserZAdd(String key, Long couponId, long userId) {
        Long r = stringRedisTemplate.execute(JOIN_STRICT_SCRIPT,
                List.of(key),
                String.valueOf(userId));
        if (r == null) throw new IllegalStateException("Redis script returned null");
        if (r == -1L) {
            throw new CouponAlreadyIssuedException("이미 해당 쿠폰을 신청했습니다: user=" + userId + ", coupon=" + couponId);
        }
        return r;
    }

    @Override
    public void setHash(String key, long quantity) {
        stringRedisTemplate.opsForHash().put(key, "value", quantity); // HSET key value <quantity>
    }

    @Override
    public void getHash(String key, String field, long quantity) {
        stringRedisTemplate.opsForHash().put(key, field, quantity); // HSET key field <quantity>
    }

    @Override
    public Long incrementHash(String key, String field, long delta) {
        Long result = stringRedisTemplate.opsForHash().increment(key, field, delta);
        if(result <= 0 ){
            throw new CouponIssuanceLimitExceededException("쿠폰 발급 한도를 초과했습니다: key=" + key);
        }
        return result;
    }

    @Override
    public List<Long> getAllMembers(String key) {
        Set<Object> members = redisTemplate.opsForSet().members(key);
        if (members == null) return List.of();
        return members.stream()
                .map(member -> objectMapper.convertValue(member, Long.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteZSetKey(String key, Long couponId) {
        Boolean result = redisTemplate.delete(key);
        return result != null && result;
    }
}

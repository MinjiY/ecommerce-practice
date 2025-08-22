//package kr.hhplus.be.server.common;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.redisson.api.Entry;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//public class RedisCommon {
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final ObjectMapper objectMapper;
//
//    public <T> Double addToSortedSet(String key, T member, Double score) {
//        return redisTemplate.opsForZSet().incrementScore(key, member, score);
//    }
//
//    public List<Entry<Object, Double>> getTopNFromSortedSetWithScores(String key, long days, Long topN) {
//        if (topN <= 0) return List.of();
//
//        long minScore = System.currentTimeMillis() - Duration.ofDays(days).toMillis(); // now - days
//        Set<ZSetOperations.TypedTuple<Object>> recentTop5 =
//                redisTemplate.opsForZSet().reverseRangeByScoreWithScores(
//                        key,
//                        minScore,                     // min = now - 3일
//                        Double.POSITIVE_INFINITY,     // max = +inf
//                        0,                            // offset
//                        topN                          // count
//                );
//        if (recentTop5 == null || recentTop5.isEmpty()) return List.of();
//
//        return recentTop5.stream()
//                .map(t -> new Entry<>(t.getValue(), t.getScore() == null ? 0.0 : t.getScore()))
//                .collect(Collectors.toList());
//    }
//}

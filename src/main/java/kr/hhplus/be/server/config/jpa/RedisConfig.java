package kr.hhplus.be.server.config.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        //String address = String.format("redis://%s:%d", host, port);
        String address = "redis://localhost:6379";
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address);
        return Redisson.create(config);
    }

//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useClusterServers()
//                .addNodeAddress(
//                        "redis://127.0.0.1:7001",
//                        "redis://127.0.0.1:7002",
//                        "redis://127.0.0.1:7003",
//                        "redis://127.0.0.1:7004",
//                        "redis://127.0.0.1:7005",
//                        "redis://127.0.0.1:7006"
//                ).setScanInterval(2000)  // 클러스터 스캔 주기 설정 (밀리초)
//                .setConnectTimeout(5000)  // 연결 시간 제한 (밀리초)
//                .setIdleConnectionTimeout(10000);  // 유휴 연결 시간 제한 (밀리초)
//
//        return Redisson.create(config);
//    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new RedissonConnectionFactory(redissonClient());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        GenericJackson2JsonRedisSerializer serializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }


}

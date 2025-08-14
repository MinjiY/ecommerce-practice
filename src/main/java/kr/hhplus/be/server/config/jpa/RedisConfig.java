package kr.hhplus.be.server.config.jpa;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress(
                        "redis://redis-7001:7001",
                        "redis://redis-7002:7002",
                        "redis://redis-7003:7003",
                        "redis://redis-7004:7004",
                        "redis://redis-7005:7005",
                        "redis://redis-7006:7006"
                );
        return Redisson.create(config);
    }
}

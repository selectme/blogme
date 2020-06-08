package com.leverx.learn.blogme.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Viktar on 06.06.2020
 */

@Configuration
@ComponentScan
@EnableCaching
public class RedisConfig {

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    @Bean
    RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
//        LocalCachedMapOptions options = LocalCachedMapOptions.defaults().maxIdle(24, TimeUnit.HOURS);
        config.put("activation-codes", new CacheConfig(24*60*1000, 12*60*1000));
//        config.put("activation-codes", new CacheConfig(24*60*1000, 12*60*1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}

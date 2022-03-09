package site.itcat.spring.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class SpringCacheConfig extends CachingConfigurerSupport implements CachingConfigurer {


    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>(16);
        config.put("attendance-security", new CacheConfig(0, 0));
        config.put("attendance-securityToken", new CacheConfig(0, 0));
        config.put("attendance-project", new CacheConfig(0, 0));
        config.put("attendance-payroll", new CacheConfig(24 * 60 * 60 * 1000, 24 * 60 * 60 * 1000));
        config.put("attendance-salaryCumulative", new CacheConfig(0, 0));
        config.put("attendance-data", new CacheConfig(0, 0));
        config.put("attendance-limit", new CacheConfig(0, 0));
        config.put("attendance-detail", new CacheConfig(2 * 24 * 60 * 60 * 1000, 2 * 24 * 60 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }


    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }

}

/**
 * vantai.com Inc. Copyright (c) 2017-2026 All Rights Reserved.
 */
package site.itcat.spring.config;

import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author : xuebengang
 * @date : 2022/3/9
 * @description : redisson编码配置
 */
@Component
public class CodecRedissonAutoConfigurationCustomizer implements RedissonAutoConfigurationCustomizer {
    @Override
    public void customize(Config config) {
        config.setCodec(new JsonJacksonCodec());
    }
}

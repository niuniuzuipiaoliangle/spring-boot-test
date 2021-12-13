package com.servyou.test.demo.test.redisTask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author
 * @title
 * @date 2020/12/03 09:28
 **/
@Configuration
public class RedisConfig {

    @Value("${fpszjk.Lock.timeout:-1}")
    private long keyTimeout;
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory connectionFactory) {
        return new RedisLockRegistry(connectionFactory, "FPSZJK_LOCK",keyTimeout < 0 ? 120*60*1000L : keyTimeout);
    }
}

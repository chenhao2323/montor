package com.github.cc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Configuration;

/**
 * CaffeineMonitorAutoConfig: ${description}
 *
 * @author chenhao
 * @version 1.0
 * @date 2020/5/28 15:21
 */
@Configuration
@AutoConfigureAfter({CacheAutoConfiguration.class})
@ConditionalOnBean(CaffeineCacheManager.class)
public class CaffeineMonitorAutoConfig {

    @Autowired
    CaffeineCacheManager caffeineCacheManager;

    public CaffeineMonitorAutoConfig(){
        System.out.println("CaffeineMonitorAutoConfig初始化");
        System.out.println(caffeineCacheManager == null);
    }
}

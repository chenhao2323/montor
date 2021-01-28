package com.github.cc.config;

import com.github.cc.controller.MonitorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public MonitorController MonitorController(){
      return new MonitorController();
    }
}

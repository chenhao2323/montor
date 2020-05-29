package com.github.cc.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

/**
 * CacheUtil: ${description}
 *
 * @author chenhao
 * @version 1.0
 * @date 2020/5/28 9:24j
 */

public class CacheUtil {

    @Autowired
    CaffeineCacheManager cacheManager;

    public static void main(String[] args){
        System.out.println(123);

        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = Caffeine.newBuilder().recordStats().build();
        cache.stats();
        //    cacheManager.getCacheNames();

      //  cache.stats();
    }

    public void test(){
        Cache cache = cacheManager.getCache("");
    }
}

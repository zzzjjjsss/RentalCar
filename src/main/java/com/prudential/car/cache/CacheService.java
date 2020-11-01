package com.prudential.car.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private Cache<String, Object> rentalCodeCache;

    public CacheService() {
        long tti = 10L;
        CacheConfiguration<String, Object> configuration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(100000L))
                .withExpiry(Expirations.timeToIdleExpiration(Duration.of(tti, TimeUnit.MINUTES))).build();
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("rentalCode", configuration).build(true);
         rentalCodeCache= cacheManager.getCache("rentalCode", String.class, Object.class);
    }
    public Date getCachedCode(String rentalCode){
        return (Date)rentalCodeCache.get(rentalCode);
    }
    public void addToCache(String rentalCode,Date createTime){
       rentalCodeCache.put(rentalCode,createTime);
    }
}

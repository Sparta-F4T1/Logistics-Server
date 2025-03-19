package com.logistic.hub.config;

import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    // 설정 구성을 먼저 진행한다.
    // Redis를 이용해서 Spring Cache를 사용할 때
    // Redis 관련 설정을 모아두는 클래스
    RedisCacheConfiguration configuration = RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues()
        .entryTtl(Duration.ofHours(1))
        // 캐시를 구분하는 접두사 설정
        .computePrefixWith(CacheKeyPrefix.simple())
        // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
        .serializeValuesWith(
            SerializationPair.fromSerializer(RedisSerializer.json())
        );

    return RedisCacheManager
        .builder(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();
  }
}
package com.logistic.hub.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.activateDefaultTyping(
        objectMapper.getPolymorphicTypeValidator(),
        ObjectMapper.DefaultTyping.NON_FINAL,
        JsonTypeInfo.As.PROPERTY
    );
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

    RedisCacheConfiguration configuration = RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues()
        .entryTtl(Duration.ofHours(1))
        // 캐시를 구분하는 접두사 설정
        .computePrefixWith(CacheKeyPrefix.simple())
        // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할것인지
        .serializeValuesWith(
            SerializationPair.fromSerializer(serializer)
        );

    return RedisCacheManager
        .builder(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();
  }
}
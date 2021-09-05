package com.example.springbootstudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

// 캐시는 실제로 현업에서 빠르게 데이터가 쌓이기 때문에
// 캐시가 금방 꽉차게 되고, 메모리 부족 현상이 올 수 있다.
// 따라서 반드시 캐시를 비우는 정책을 잡고 꾸준히 캐시를 비우거나 갱신하는 로직이 필요하다.
// 그 중 기본이 TTL (Properties 에서 설정 가능, 단, 캐시를 수동 설정 할 경우 동작하지 않음)
// 그냥 쓸거면 프로퍼티에서 하고, 잭슨을 적용할거면 프로퍼티는 깔끔하게 포기하고 수동설정!

//@RequiredArgsConstructor
@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

//    private final RedisConnectionFactory redisConnectionFactory;
//
//    @Override
//    public CacheManager cacheManager() {
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
//                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
//                    .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))).build();
//    }


    // 수동으로 직렬화 기능을 추가해줄 수 있다.
    // redis 의 기본 Serializer 를 json 으로 사용하기 위한 설정
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(name -> name + ":") // 키 네임에서 콜론이 두개나오는걸 한개만 나오도록 수정
                .entryTtl(Duration.ofSeconds(10))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}

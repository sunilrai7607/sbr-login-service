package com.sbr.platform.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@EnableCaching
@Slf4j
public class RedisSessionConfig extends CachingConfigurerSupport {


    @Bean(name = "springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        log.info("Inside springSessionDefaultRedisSerializer ");
        return new CustomRedisDataSerializer();
    }

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConFactory
//                = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);
//        return jedisConFactory;
//    }
//
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate() {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        return template;
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager() {
//        RedisCacheManager redisCacheManager = RedisCacheManager
//                .RedisCacheManagerBuilder
//                .fromConnectionFactory(jedisConnectionFactory())
//                .cacheDefaults(redisCacheConfig())
//                .build();
//        redisCacheManager.setTransactionAware(true);
//        return redisCacheManager;
//    }
//
//    private RedisCacheConfiguration redisCacheConfig() {
//        RedisSerializationContext.SerializationPair<SharedSessionContext> jsonSerializer = RedisSerializationContext.SerializationPair.fromSerializer(customJsonRedisSerializer());
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
//                .defaultCacheConfig()
//                .disableCachingNullValues();
//        //.entryTtl(Duration.ofSeconds(10));
//        redisCacheConfiguration.usePrefix();
//        redisCacheConfiguration.serializeValuesWith(jsonSerializer);
//        return redisCacheConfiguration;
//    }
//
//    @Bean
//    public JsonRedisSerializer customJsonRedisSerializer() {
//        return new JsonRedisSerializer();
//    }

}
package com.deni.transaction.springcrudtransaction.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig {

    Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    // kita inject cacheManager untuk caching
    @Autowired private CacheManager cacheManager;

    // kita buat field nanti hostnya diambil dari applicaiton.yml
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    // kita perlu menambahkan redis template
    // redis template berguna untuk supaya project kita bisa
    // berkomunikasi dengan si redis,

    @Bean
    public  RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Serializable> templateSiRedis = new RedisTemplate<>();

        templateSiRedis.setKeySerializer(new StringRedisSerializer());
        templateSiRedis.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        templateSiRedis.setConnectionFactory(redisConnectionFactory);
        return templateSiRedis;
    }


    // untuk cache
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheConfiguration redisCacheConfiguration = configuration
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();

        return redisCacheManager;
    }

    public  void clearCache(){
        logger.info(" cache dibersihkna");
        Jedis jedis = new Jedis(redisHost, redisPort, 10000);
        jedis.flushAll();
        jedis.close();
    }


}

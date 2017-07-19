package com.xiangzhu.plat.domain;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * Created by lqli on 2017/7/19 10:50.
 * redis缓存配置
 *
 * @author lqli
 */
public class RedisCacheConfig extends CachingConfigurerSupport {
    private volatile JedisConnectionFactory jedisConnectionFactory;
    private volatile RedisTemplate<String, String> redisTemplate;
    private volatile RedisCacheManager redisCacheManager;

    public RedisCacheConfig() {
        super();
    }

    public RedisCacheConfig(JedisConnectionFactory jedisConnectionFactory, RedisTemplate<String,String> redisTemplate,
                            RedisCacheManager redisCacheManager) {
        super();
        this.jedisConnectionFactory = jedisConnectionFactory;
        this.redisTemplate = redisTemplate;
        this.redisCacheManager = redisCacheManager;
    }

    public JedisConnectionFactory jedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public RedisTemplate<String, String> redisTemplate() {
        return redisTemplate;
    }

    public RedisCacheManager redisCacheManager() {
        return redisCacheManager;
    }

    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(o.getClass().getName());
                stringBuilder.append(method.getName());
                for (Object obj : objects) {
                    stringBuilder.append(obj.toString());
                }
                return stringBuilder.toString();
            }
        };
    }
}

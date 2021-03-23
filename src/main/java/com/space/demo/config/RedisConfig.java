package com.space.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
     @Value("${spring.redis.host}")
     private String host;

    @Value("${spring.redis.port}")
     private int port;

    @Value("${spring.redis.timeout}")
     private int timeout;

    @Value("${spring.redis.jedis.pool.maxActive}")
     private int maxActive;

    @Value("${spring.redis.jedis.pool.maxWait}")
     private int maxWait;

    @Value("${spring.redis.jedis.pool.maxIdle}")
     private int maxIdle;

    @Value("${spring.redis.jedis.pool.minIdle}")
     private int minIdle;

    @Value("${spring.redis.password}")
     private String password;

    @Value("${spring.redis.username}")
     private String username;

     @Bean
     public JedisPool redisPoolFactory(){
         JedisPoolConfig config = new JedisPoolConfig();
         config.setMaxTotal(maxActive);
         config.setMaxWaitMillis(maxWait);
         config.setMaxIdle(maxIdle);
         config.setMinIdle(minIdle);
         JedisPool pool = new JedisPool(config,host,port,timeout,password,false);
         return pool;
     }
}

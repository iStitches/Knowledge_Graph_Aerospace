package com.space.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
@RunWith(SpringRunner.class)

class DemoApplicationTests {
    @Autowired
    JedisPool jedisPool;

    @Test
    void contextLoads() {
        Jedis resource = jedisPool.getResource();
        resource.auth("123456");
        System.out.println(resource.ping());
    }

}

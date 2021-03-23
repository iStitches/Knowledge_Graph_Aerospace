package com.space.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {
    @Autowired
    JedisPool jedisPool;

    /**
     * 从库中获取数据
     */
    public void setHash(String name,String hashKey,String hashValue){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(name,hashKey,hashValue);
        } catch (Exception e) {
            e.printStackTrace();
            if(jedis != null)
                jedis.close();
        } finally {
            if(jedis != null)
                jedis.close();
        }
    }

    /**
     * 从库中取出数据
     */
    public String getHash(String name,String hashKey){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(name,hashKey);
        } catch (Exception e) {
            e.printStackTrace();
            if(jedis != null)
                jedis.close();
        } finally {
            if(jedis != null)
                jedis.close();
        }
        return null;
    }

    public void setValue(String name,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(name,value);
        } catch (Exception e) {
            e.printStackTrace();
            if(jedis != null)
                jedis.close();
        } finally {
            if(jedis != null)
                jedis.close();
        }
    }

    public String getValue(String name){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(name);
        } catch (Exception e) {
            e.printStackTrace();
            if(jedis != null)
                jedis.close();
        } finally {
            if(jedis != null)
                jedis.close();
        }
        return null;
    }
}

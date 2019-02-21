package moku.concurrency.miaosha.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.*;

/**
 * Redis工具类
 */

@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    private void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    //给某个key设值
    public void set(String key, String value) {
        Jedis jedis = getJedis();
        try {
            byte[] keyBytes = key.getBytes();
            byte[] valueBytes = value.getBytes();
            jedis.set(keyBytes, valueBytes);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }

    }

    //根据key获取value
    public byte[] get(String key) {
        Jedis jedis = getJedis();
        byte[] result = null;
        try {
            byte[] keyBytes = key.getBytes();
            result = jedis.get(keyBytes);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    //根据键值获取value
    public byte[] hashGet(String key, String field) {
        Jedis jedis = getJedis();
        byte[] result = null;
        try {
            byte[] keyBytes = key.getBytes();
            byte[] fieldBytes = field.getBytes();
            result = jedis.hget(keyBytes, fieldBytes);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public void hashSet(String key, String field, String value) {
        Jedis jedis = getJedis();
        try {
            byte[] keyBytes = key.getBytes();
            byte[] fieldBytes = field.getBytes();
            byte[] valueBytes = value.getBytes();
            jedis.hset(keyBytes, fieldBytes, valueBytes);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }

    }


    public Map<String, byte[]> hashAllGet(String key) {
        Jedis jedis = getJedis();
        Map<String, byte[]> result = new HashMap();
        try {
            byte[] keyBytes = key.getBytes();
            Map<byte[], byte[]> map = jedis.hgetAll(keyBytes);
            Set<Map.Entry<byte[], byte[]>> valueSet = map.entrySet();
            for (Map.Entry<byte[], byte[]> entry : valueSet) {
                result.put(key.toString(), entry.getValue());
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    //判断key是否存在
    public boolean existKey(String key) {
        Jedis jedis = getJedis();
        boolean result = false;
        try {
            byte[] keyBytes = key.getBytes();
            result = jedis.exists(keyBytes);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }


    public List<byte[]> brpop(int timeout, String key) {
        Jedis jedis = getJedis();
        List<byte[]> result = new ArrayList();
        try {
            byte[] keyBytes = key.getBytes();
            result = jedis.brpop(timeout, keyBytes);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

}

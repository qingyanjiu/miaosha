package moku.concurrency.miaosha.service.impl;

import moku.concurrency.miaosha.service.IDistributeLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

@Service
public class DistributeLockServiceImpl implements IDistributeLockService {

    public static final int LOCK_EXPIRE_SECOND = 60;

    @Autowired
    private JedisPool jedisPool;


    @Override
    public String getLock(String resourceId, int expireSecond) {
        Jedis conn = null;
        long endTime = System.currentTimeMillis() + expireSecond * 1000;
        String retIdentifier = null;
        try {
            // 获取连接
            conn = jedisPool.getResource();
            while (System.currentTimeMillis() < endTime) {
                String lockIdentifier = UUID.randomUUID().toString();
                long re = conn.setnx(resourceId, lockIdentifier);
                if (re == 1) {
                    conn.expire(resourceId, LOCK_EXPIRE_SECOND);
                    return lockIdentifier;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("获取锁失败，欧耶--"+Thread.currentThread().getName());
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    @Override
    public boolean releaseLock(String resourceId, String lockIdentifier) {
        Jedis conn = null;
        boolean ret = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始事务
                conn.watch(resourceId);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (lockIdentifier.equals(conn.get(resourceId))) {
                    Transaction transaction = conn.multi();
                    transaction.del(resourceId);
                    List<Object> results = transaction.exec();
                    //事务提交失败，再试一次
                    if (results == null) {
                        System.out.println("transaction.exec() returns null");
                        continue;
                    }
                    ret = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return ret;
    }
}

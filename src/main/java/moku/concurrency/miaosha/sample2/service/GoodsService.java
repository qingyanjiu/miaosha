package moku.concurrency.miaosha.sample2.service;

import moku.concurrency.miaosha.sample2.dao.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class GoodsService implements IGoodsService{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostConstruct
    @Override
    public void init() {
        for(int i=0;i<150;i++) {
            stringRedisTemplate.opsForList().leftPush("token","token_"+i);
        }
    }

    @Override
    public void sell(Map params) {
        String token = stringRedisTemplate.opsForList().rightPop("token");
        if(token != null) {
            logger.info("token:"+token);
            boolean result = goodsMapper.sell(params);
            if (result)
                logger.info("抢到手机!");
            else
                logger.error("没手机了！");
        } else {
            logger.error("token都没拿到，再见！");
        }
    }
}

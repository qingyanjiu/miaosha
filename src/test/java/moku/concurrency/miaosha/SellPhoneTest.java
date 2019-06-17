package moku.concurrency.miaosha;

import moku.concurrency.miaosha.sample2.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellPhoneTest {

    @Autowired
    IGoodsService goodsService;

    @Test
    public void testSell() throws InterruptedException {
        int threadNumber = 2000;
        Map params = new HashMap();
        params.put("type",1);
        CyclicBarrier cb = new CyclicBarrier(threadNumber);
        CountDownLatch cdl = new CountDownLatch(threadNumber);

        for(int i=0;i<threadNumber;i++) {
            new Thread(()->{
                try {
                    cb.await();
                    goodsService.sell(params);
                    cdl.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        cdl.await();
    }
}

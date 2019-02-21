package moku.concurrency.miaosha;

import moku.concurrency.miaosha.utils.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTests {

    @Autowired
    JedisUtil jedisUtil;

    @Test
    public void testBrpop() {
        while (true) {
            try {
                List<byte[]> list = jedisUtil.brpop(3000, "test");
                if (list != null && list.size() > 0) {
                    System.out.println(new String(list.get(1)));
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

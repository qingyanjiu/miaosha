package moku.concurrency.miaosha.queue;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Receiver {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues="miaosha")
    public void receiveMiaoshaMessage(Map params) {
        logger.info("Receive:"+params);
    }

}

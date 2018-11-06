package moku.concurrency.miaosha.queue;

import com.rabbitmq.client.Channel;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Receiver {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = "miaosha")
    public void receiveMiaoshaMessage(Map params, Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, false);
        logger.info("Receive:" + params);
    }

}

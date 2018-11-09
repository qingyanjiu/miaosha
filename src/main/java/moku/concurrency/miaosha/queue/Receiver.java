package moku.concurrency.miaosha.queue;

import com.rabbitmq.client.Channel;
import moku.concurrency.miaosha.service.IRedPackageService;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class Receiver {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private IRedPackageService redPackageService;

    @RabbitListener(queues = Sender.DIRECT_EXCHANGE_MIAOSHA_QUEUE_NAME_MIAOSHA_ADD)
    public void receiveMiaoshaAddMessage(Map params, Message message, Channel channel) throws IOException {
        try {
            logger.debug("Receive:" + params);
            redPackageService.add(params);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            logger.error(e.getMessage());
        }
    }

}

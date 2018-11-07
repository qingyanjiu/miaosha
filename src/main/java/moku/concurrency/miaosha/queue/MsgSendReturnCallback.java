package moku.concurrency.miaosha.queue;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MsgSendReturnCallback implements RabbitTemplate.ReturnCallback {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MsgSendReturnCallback.class);

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.debug("收到回调,return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
    }
}

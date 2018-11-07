package moku.concurrency.miaosha.queue;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MsgSendConfirmCallBack.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.debug("消息发送到exchange成功,id: {}", correlationData.getId());
        } else {
            logger.debug("消息发送到exchange失败,原因: {}", cause);
        }
    }
}

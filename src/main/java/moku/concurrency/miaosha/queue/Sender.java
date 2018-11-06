package moku.concurrency.miaosha.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate template;

    public void sendMiaosha(Map params){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        template.convertAndSend("miaosha", params, correlationData);
    }
}

package moku.concurrency.miaosha.queue;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class Sender {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String DIRECT_EXCHANGE_MIAOSHA_QUEUE_NAME_MIAOSHA_ADD = "miaosha_add";

    public static final String DIRECT_EXCHANGE_MIAOSHA_ROUTING_KEY = "miaosha_queue";

    public static final String DIRECT_EXCHANGE_MIAOSHA_NAME = "miaosha_exchange";

    public void send(String exchangeName,String routingKey, Map params){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(exchangeName,routingKey, params, correlationData);
    }
}
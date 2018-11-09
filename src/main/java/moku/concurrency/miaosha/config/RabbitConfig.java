package moku.concurrency.miaosha.config;

import moku.concurrency.miaosha.queue.MsgSendConfirmCallBack;
import moku.concurrency.miaosha.queue.MsgSendReturnCallback;
import moku.concurrency.miaosha.queue.Sender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public DirectExchange miaoshaExchange() {
        return new DirectExchange(Sender.DIRECT_EXCHANGE_MIAOSHA_NAME, true, false);
    }
    @Bean
    public Queue miaoshaQueue() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-dead-letter-exchange", "dead_letter_exchange");//设置死信交换机，失败后消息发往死信交换机
        map.put("x-dead-letter-routing-key", "mail_queue_fail");//设置死信routingKey,，失败后消息发往死信交换机的routingKey
        Queue queue = new Queue(Sender.DIRECT_EXCHANGE_MIAOSHA_ROUTING_KEY,true, false, false, map);
        return queue;
    }
    @Bean
    public Binding miaoshaBinding() {
        return BindingBuilder.bind(miaoshaQueue()).to(miaoshaExchange())
                .with(Sender.DIRECT_EXCHANGE_MIAOSHA_ROUTING_KEY);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Sender.FANOUT_EXCHANGE_NAME, true, false);
    }
    @Bean
    public Queue fanoutQueue() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-dead-letter-exchange", "dead_letter_exchange");//设置死信交换机，失败后消息发往死信交换机
        map.put("x-dead-letter-routing-key", "mail_queue_fail");//设置死信routingKey,，失败后消息发往死信交换机的routingKey
        Queue queue = new Queue(Sender.FANOUT_QUEUE_NAME,true, false, false, map);
        return queue;
    }
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }


    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange("dead_letter_exchange", true, false);
    }
    @Bean
    public Queue deadQueue(){
        Queue queue = new Queue("dead", true);
        return queue;
    }
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange())
                .with("mail_queue_fail");
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        // template.setMessageConverter(); 可以自定义消息转换器  默认使用的JDK的，所以消息对象需要实现Serializable
        // template.setMessageConverter(new Jackson2JsonMessageConverter());

        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());

        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        template.setReturnCallback(msgSendReturnCallback());
        template.setMandatory(true);
        return template;
    }

    @Bean
    MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

    @Bean
    MsgSendReturnCallback msgSendReturnCallback(){
        return new MsgSendReturnCallback();
    }

}

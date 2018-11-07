package moku.concurrency.miaosha.config;

import moku.concurrency.miaosha.queue.MsgSendConfirmCallBack;
import moku.concurrency.miaosha.queue.MsgSendReturnCallback;
import moku.concurrency.miaosha.queue.Sender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Queue queue() {
        return new Queue(Sender.ROUTING_KEY_MIAOSHA_ADD);
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

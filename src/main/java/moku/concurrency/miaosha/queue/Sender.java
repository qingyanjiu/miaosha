package moku.concurrency.miaosha.queue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate template;

    public void sendMiaosha(Map params){
        template.convertAndSend("miaosha",params);
    }
}

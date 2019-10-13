package wang.ismy.edu.manage_cms.client.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MY
 * @date 2019/10/13 19:43
 */
@Configuration
public class RabbitMqConfig {

    @Value("${edu.mq.queue}")
    public String queueName;

    @Value("${edu.mq.routingKey}")
    public String routingKey;

    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.directExchange("ex_routing_cms_postpage").durable(true).build();
    }

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }

    @Bean
    public Binding binding(Queue queue,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }


}

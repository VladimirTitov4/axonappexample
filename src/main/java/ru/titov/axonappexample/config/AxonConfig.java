package ru.titov.axonappexample.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AxonConfig {

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.usingSubscribingEventProcessors();
    }

    private static final String AXON_QUEUE = "axonAppQueue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(AXON_QUEUE).build();
    }

//    @Bean
//    public SpringAMQPMessageSource axonQueueMessageSource(AMQPMessageConverter messageConverter) {
//        return new SpringAMQPMessageSource(messageConverter) {
//
//            @RabbitListener(queues = AXON_QUEUE)
//            @Transactional
//            @Override
//            public void onMessage(Message message, Channel channel) {
//                log.debug("[AMQP] Processing message: {}, on channel: {}", message, channel);
//                super.onMessage(message, channel);
//            }
//        };
//    }
}

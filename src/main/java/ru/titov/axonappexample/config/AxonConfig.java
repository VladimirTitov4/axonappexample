package ru.titov.axonappexample.config;

import io.jaegertracing.internal.JaegerTracer;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.extensions.tracing.OpenTraceDispatchInterceptor;
import org.axonframework.extensions.tracing.OpenTraceHandlerInterceptor;
import org.axonframework.extensions.tracing.TracingCommandGateway;
import org.axonframework.extensions.tracing.TracingQueryGateway;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
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
        configurer.usingTrackingEventProcessors();
    }

    private static final String AXON_QUEUE = "axonAppQueue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus, JaegerTracer jaegerTracer, OpenTraceDispatchInterceptor openTraceDispatchInterceptor, OpenTraceHandlerInterceptor openTraceHandlerInterceptor) {
        commandBus.registerHandlerInterceptor(openTraceHandlerInterceptor);
        TracingCommandGateway tracingCommandGateway = TracingCommandGateway.builder().tracer(jaegerTracer).delegateCommandBus(commandBus).build();
        tracingCommandGateway.registerDispatchInterceptor(openTraceDispatchInterceptor);
        return tracingCommandGateway;
    }

    @Bean
    public QueryGateway queryGateway(JaegerTracer jaegerTracer, QueryBus queryBus, OpenTraceDispatchInterceptor openTraceDispatchInterceptor, OpenTraceHandlerInterceptor openTraceHandlerInterceptor) {
        queryBus.registerHandlerInterceptor(openTraceHandlerInterceptor);
        TracingQueryGateway tracingQueryGateway = TracingQueryGateway.builder().delegateQueryBus(queryBus).tracer(jaegerTracer).build();
        tracingQueryGateway.registerDispatchInterceptor(openTraceDispatchInterceptor);
        return tracingQueryGateway;
    }

//    @Bean
//    public Queue queue() {
//        return QueueBuilder.durable(AXON_QUEUE).build();
//    }

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

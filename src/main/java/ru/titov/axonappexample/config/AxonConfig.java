package ru.titov.axonappexample.config;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.extensions.tracing.OpenTraceDispatchInterceptor;
import org.axonframework.extensions.tracing.OpenTraceHandlerInterceptor;
import org.axonframework.extensions.tracing.TracingCommandGateway;
import org.axonframework.extensions.tracing.TracingProvider;
import org.axonframework.extensions.tracing.TracingQueryGateway;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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

//    @Bean
//    public JaegerTracer jaeger() {
//        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = io.jaegertracing.Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
//        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv().withLogSpans(false);
//        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("jaeger tutorial").withSampler(samplerConfig).withReporter(reporterConfig);
//        return jaegerTracer;
//    }


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
//    public CorrelationDataProvider tracingProvider(JaegerTracer jaegerTracer) {
//        return new TracingProvider(jaegerTracer);
//    }

//    @Bean
//    public CommandGateway tracingCommandGateway(CommandGateway commandGateway) {
//        return TracingCommandGateway.builder().delegateCommandGateway(commandGateway).build();
//    }


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

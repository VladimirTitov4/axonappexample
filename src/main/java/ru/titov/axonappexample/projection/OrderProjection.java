package ru.titov.axonappexample.projection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import ru.titov.axonappexample.commands.DeliverOrderCommand;
import ru.titov.axonappexample.commands.PayOrderCommand;
import ru.titov.axonappexample.events.OrderCreatedEvent;
import ru.titov.axonappexample.events.OrderDeliveredEvent;
import ru.titov.axonappexample.events.OrderPayedEvent;
import ru.titov.axonappexample.queries.OrderQuery;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProjection {

    private final CommandGateway commandGateway;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        log.info("#1 order has been created");
//        commandGateway.send(new PayOrderCommand(event.getId(), event.getName()));
    }

    @EventHandler
    public void on(OrderPayedEvent event) {
        log.info("#2 order has been payed");
//        commandGateway.send(new DeliverOrderCommand(event.getId(), event.getName()));
    }

    @EventHandler
    public void on(OrderDeliveredEvent event) {
        log.info("#3 order has been delivered");
    }

    @QueryHandler
    public String handle(OrderQuery query) {
        return "Orderrrrr";
    }
}

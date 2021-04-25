package ru.titov.axonappexample.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import ru.titov.axonappexample.commands.CreateOrderCommand;
import ru.titov.axonappexample.commands.DeliverOrderCommand;
import ru.titov.axonappexample.commands.PayOrderCommand;
import ru.titov.axonappexample.events.OrderCreatedEvent;
import ru.titov.axonappexample.events.OrderDeliveredEvent;
import ru.titov.axonappexample.events.OrderPayedEvent;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getId(),
                command.getName()
        ));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(PayOrderCommand command) {
        AggregateLifecycle.apply(new OrderPayedEvent(
                command.getId(),
                command.getName()
        ));
    }

    @EventSourcingHandler
    public void on(OrderPayedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(DeliverOrderCommand command) {
        AggregateLifecycle.apply(new OrderDeliveredEvent(
                command.getId(),
                command.getName()
        ));
    }

    @EventSourcingHandler
    public void on(OrderDeliveredEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }
}

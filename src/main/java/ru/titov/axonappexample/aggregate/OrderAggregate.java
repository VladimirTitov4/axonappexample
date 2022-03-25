package ru.titov.axonappexample.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import ru.titov.axonappexample.commands.CreateOrderCommand;
import ru.titov.axonappexample.commands.PayOrderCommand;
import ru.titov.axonappexample.events.OrderCreatedEvent;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command, CommandGateway commandGateway) {
        commandGateway.send(new PayOrderCommand(UUID.randomUUID(), command.getName()));
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
}

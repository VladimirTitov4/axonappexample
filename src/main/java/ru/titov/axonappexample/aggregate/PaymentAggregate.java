package ru.titov.axonappexample.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import ru.titov.axonappexample.commands.DeliverOrderCommand;
import ru.titov.axonappexample.commands.PayOrderCommand;
import ru.titov.axonappexample.events.OrderPayedEvent;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public PaymentAggregate(PayOrderCommand command, CommandGateway commandGateway) {
        commandGateway.send(new DeliverOrderCommand(UUID.randomUUID(), command.getName()));
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
}

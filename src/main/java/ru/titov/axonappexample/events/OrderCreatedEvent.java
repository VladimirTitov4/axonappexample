package ru.titov.axonappexample.events;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class OrderCreatedEvent {
    @TargetAggregateIdentifier
    UUID id;
    String name;
}

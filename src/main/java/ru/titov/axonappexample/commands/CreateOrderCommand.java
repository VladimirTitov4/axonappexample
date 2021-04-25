package ru.titov.axonappexample.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    UUID id;
    String name;
}

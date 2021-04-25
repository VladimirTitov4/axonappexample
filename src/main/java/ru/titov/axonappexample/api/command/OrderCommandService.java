package ru.titov.axonappexample.api.command;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import ru.titov.axonappexample.commands.CreateOrderCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<UUID> createOrder(String name) {
        return commandGateway.send(new CreateOrderCommand(
                UUID.randomUUID(),
                name
        ));
    }
}

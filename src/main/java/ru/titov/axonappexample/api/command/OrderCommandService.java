package ru.titov.axonappexample.api.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import ru.titov.axonappexample.commands.CreateOrderCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<UUID> createOrder(String name) {
        UUID id = UUID.randomUUID();
        System.out.println("id = " + id);
        return commandGateway.send(new CreateOrderCommand(
                id,
                name
        ));
    }
}

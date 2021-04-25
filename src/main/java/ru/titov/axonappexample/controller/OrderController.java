package ru.titov.axonappexample.controller;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.titov.axonappexample.commands.CreateOrderCommand;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final CommandGateway commandGateway;

    @PutMapping("/createOrder")
    public CompletableFuture<String> createOrder(@RequestParam("name") String name) {
        return commandGateway.send(new CreateOrderCommand(UUID.randomUUID(), name));
    }
}

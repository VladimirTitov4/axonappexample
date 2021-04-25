package ru.titov.axonappexample.api.graphql.mutation;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.titov.axonappexample.api.command.OrderCommandService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderMutationResolver implements GraphQLMutationResolver {
    private final OrderCommandService service;

    public CompletableFuture<UUID> createOrder(String name) {
        return service.createOrder(name);
    }
}

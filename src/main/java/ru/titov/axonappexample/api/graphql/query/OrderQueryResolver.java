package ru.titov.axonappexample.api.graphql.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import ru.titov.axonappexample.queries.OrderQuery;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderQueryResolver implements GraphQLQueryResolver {

    private final QueryGateway queryGateway;

    public CompletableFuture<String> hello() {
        return queryGateway.query(new OrderQuery(UUID.randomUUID()), ResponseTypes.instanceOf(String.class));
    }
}

package ru.titov.axonappexample.api.graphql.query;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryResolver implements GraphQLQueryResolver {
    public String hello() {
        return "hello";
    }
}

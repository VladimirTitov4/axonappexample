package ru.titov.axonappexample.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.language.SourceLocation;

import java.util.List;

import static java.util.Collections.singletonList;

public class GraphQLValidationException extends GraphQLException implements GraphQLError {

    private final String path;

    public GraphQLValidationException(String path, String message) {
        super(message);
        this.path = path;
    }

    @Override
    public List<Object> getPath() {
        return singletonList(path);
    }

    /*
    Avoid redundant fields in output.
     */
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    /*
    Avoid redundant fields in output.
     */
    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

}

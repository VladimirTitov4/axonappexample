package ru.titov.axonappexample.config;

import graphql.GraphQLError;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.GraphQLScalarType;
import graphql.validation.interpolation.MessageInterpolator;
import graphql.validation.rules.ValidationEnvironment;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.titov.axonappexample.exception.GraphQLValidationException;

import java.util.Map;
import java.util.UUID;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public ValidationSchemaWiring schemaWiring(MessageInterpolator messageInterpolator) {
        ValidationRules rules = ValidationRules.newValidationRules()
                .messageInterpolator(messageInterpolator)
                .build();
        return new ValidationSchemaWiring(rules);
    }

    @Bean
    public MessageInterpolator messageInterpolator() {
        return new MessageInterpolator() {

            @Override
            public GraphQLError interpolate(String messageTemplate, Map<String, Object> messageParams,
                                            ValidationEnvironment validationEnvironment) {
                return new GraphQLValidationException(
                        getJsonPath(messageParams, validationEnvironment),
                        messageTemplate
                );
            }

            private String getJsonPath(Map<String, Object> messageParams, ValidationEnvironment validationEnvironment) {
                StringBuilder jsonPathBuilder = new StringBuilder("$");
                String pathToErrorField = messageParams.get("path").toString().substring(
                        validationEnvironment.getExecutionPath().toString().length()
                );
                jsonPathBuilder.append(pathToErrorField.replace("/", "."));
                return jsonPathBuilder.toString();
            }

        };
    }

    @Bean
    public GraphQLScalarType uuidScalar() {
        return GraphQLScalarType.newScalar().name("UUID").coercing(new Coercing<UUID, String>() {
            @Override
            public String serialize(Object dataFetcherResult) {
                return String.valueOf(dataFetcherResult);
            }

            @Override
            public UUID parseValue(Object input) {
                if (input instanceof String) {
                    return UUID.fromString(input.toString());
                }
                throw new CoercingParseValueException("Expected a 'String' but was '" + input.getClass().getName() + "'.");
            }

            @Override
            public UUID parseLiteral(Object input) {
                if (input instanceof StringValue) {
                    return UUID.fromString(((StringValue) input).getValue());
                }
                throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + input.getClass().getName() + "'.");
            }
        }).build();
    }
}

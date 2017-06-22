package com.opi.zoo.graphql.executor

import com.opi.zoo.graphql.schema.GraphQLSchemaService
import graphql.GraphQL
import graphql.GraphQLException
import graphql.execution.ExecutorServiceExecutionStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

import javax.annotation.PostConstruct
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import static GraphQL.newGraphQL

@Component
class GraphQlExecutorImpl implements GraphQlExecutor {
    @Autowired
    GraphQLSchemaService schemaService

    private GraphQL graphQL

    /**
     * Setup Execution Strategy
     * https://goo.gl/ewG2hL
     */
    @PostConstruct
    private void postConstruct() {
        def queue = new LinkedBlockingQueue<Runnable>() {
            @Override
            boolean offer(Runnable e) {
                /* queue that always rejects tasks */
                return false
            }
        }
        def strategy = new ExecutorServiceExecutionStrategy(new ThreadPoolExecutor(
                2,
                2,
                10,
                TimeUnit.SECONDS,
                queue,
                new CustomizableThreadFactory("graphql-thread-"),
                new ThreadPoolExecutor.CallerRunsPolicy()))

        graphQL = newGraphQL(schemaService.getSchema())
                .queryExecutionStrategy(strategy)
                .build()
    }

    Object executeRequest(Map body) {
        def query = (String) body.get("query")
        def operationName = (String) body.get("operationName")
        def variables = getVariablesFromRequest(body)
        def context = new HashMap<String, Object>()

        def executionResult = graphQL.execute(query, operationName, context, variables)

        def result = new LinkedHashMap<String, Object>()

        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors())
        }
        result.put("data", executionResult.getData())

        return result
    }

    private Map<String, Object> getVariablesFromRequest(Map requestBody) {
        def variablesFromRequest = requestBody.get("variables")

        if (variablesFromRequest == null) {
            return Collections.emptyMap()
        }

        if (variablesFromRequest instanceof String) {
            if (StringUtils.hasText((String) variablesFromRequest)) {
                return getVariablesMapFromString((String) variablesFromRequest)
            }
        } else if (variablesFromRequest instanceof Map) {
            return (Map<String, Object>) variablesFromRequest
        } else {
            throw new GraphQLException("Incorrect variables")
        }

        return Collections.emptyMap()
    }

    // FIXME
    private Map<String, Object> getVariablesMapFromString(String variablesFromRequest) {
        try {
            return jacksonObjectMapper.readValue(variablesFromRequest, typeRefReadJsonString)
        } catch (IOException exception) {
            throw new GraphQLException("Cannot parse variables", exception)
        }
    }
}

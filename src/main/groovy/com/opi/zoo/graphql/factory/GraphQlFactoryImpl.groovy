package com.opi.zoo.graphql.factory

import com.opi.zoo.graphql.schema.GraphQLSchemaService
import graphql.GraphQL
import graphql.execution.ExecutorServiceExecutionStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import org.springframework.stereotype.Component

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

import static graphql.GraphQL.newGraphQL

@Component
class GraphQlFactoryImpl implements GraphQlFactory {
    @Autowired
    GraphQLSchemaService schemaService

    GraphQL createGraphQL() {
        def queue = new LinkedBlockingQueue<Runnable>() {
            @Override
            boolean offer(Runnable e) {
                /* queue that always rejects tasks */
                return false
            }
        }

        // todo: these could all be configured in app props
        def strategy = new ExecutorServiceExecutionStrategy(new ThreadPoolExecutor(
                2,
                2,
                10,
                TimeUnit.SECONDS,
                queue,
                new CustomizableThreadFactory("graphql-thread-"),
                new ThreadPoolExecutor.CallerRunsPolicy()))

        return newGraphQL(schemaService.getSchema())
                .queryExecutionStrategy(strategy)
                .build()
    }
}

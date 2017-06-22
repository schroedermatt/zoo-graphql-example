package com.opi.zoo.graphql.executor

import com.opi.zoo.graphql.factory.GraphQlFactory
import graphql.GraphQL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class GraphQlExecutorImpl implements GraphQlExecutor {
    @Autowired
    GraphQlFactory graphQLFactory

    private GraphQL graphQL

    @PostConstruct
    protected void postConstruct() {
        graphQL = graphQLFactory.createGraphQL()
    }

    HashMap executeRequest(Map body) {
        def query = (String) body.get("query")
        def operationName = (String) body.get("operationName")

        // todo: parse "variables"
        // def variables = getVariablesFromRequest(body)

        def executionResult = graphQL.execute(query, operationName, [:], [:])
        def result = new LinkedHashMap<String, Object>()

        if (executionResult.getErrors().size() > 0) {
            result.put("errors", executionResult.getErrors())
        }
        result.put("data", executionResult.getData())

        return result
    }
}

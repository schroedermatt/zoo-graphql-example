package com.opi.zoo.graphql.executor

import com.opi.zoo.graphql.factory.GraphQlFactory
import graphql.ExecutionResultImpl
import graphql.GraphQL
import spock.lang.Specification

class GraphQlExecutorSpec extends Specification {

    GraphQlExecutor sut
    GraphQL mockGraphQL

    def setup() {
        mockGraphQL = Mock(GraphQL)
        sut = new GraphQlExecutorImpl(
                graphQL: mockGraphQL,
                graphQLFactory: Mock(GraphQlFactory)
        )
    }

    def 'executeRequest returns data'() {
        given:
        def testQuery = '{query:keepers {name}}'
        def data = 'this is some data'
        def errors = ['these','are','errors']
        def execResult = new ExecutionResultImpl(data, errors)

        when:
        def result = sut.executeRequest(['query':testQuery, 'operationName':'operationOfThings'])

        then:
        mockGraphQL.execute(testQuery, _, [:], [:]) >> execResult

        result['data'] == data
        result['errors'] == errors
    }
}

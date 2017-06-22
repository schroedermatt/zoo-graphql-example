package com.opi.zoo.graphql.factory

import com.opi.zoo.graphql.schema.GraphQLSchemaService
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import spock.lang.Specification

class GraphQlFactorySpec extends Specification {
    def sut = new GraphQlFactoryImpl(
        schemaService: Mock(GraphQLSchemaService)
    )

    def 'factory produces expected GraphQL object'() {
        given:
        def schema = Mock(GraphQLSchema)

        when:
        GraphQL result = sut.createGraphQL()

        then:
        1 * sut.schemaService.getSchema() >> schema

        result
        result.getAt('graphQLSchema') == schema
        result.getAt('queryStrategy') // todo: add more assertions?
    }
}

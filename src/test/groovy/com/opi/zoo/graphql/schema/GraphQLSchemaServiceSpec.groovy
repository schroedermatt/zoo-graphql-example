package com.opi.zoo.graphql.schema

import com.opi.zoo.graphql.schema.builder.IDLSchemaBuilder
import com.opi.zoo.graphql.schema.builder.ProgrammaticSchemaBuilder
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import spock.lang.Specification
import spock.lang.Unroll

class GraphQLSchemaServiceSpec extends Specification {

    @Unroll
    def "correct schema returned: #desc"() {
        given:
        def sut = new GraphQLSchemaServiceImpl(
            useIDL: useIDL,
            idlSchema: Mock(IDLSchemaBuilder),
            programmaticSchema: Mock(ProgrammaticSchemaBuilder)
        )
        def blankSchema = new GraphQLSchema(new GraphQLObjectType("blank", "", [], []))

        when:
        sut.getSchema()

        then: 'the correct schema builder is called'
        (useIDL ? 1 : 0) * sut.idlSchema.getSchema() >> blankSchema
        (useIDL ? 0 : 1) * sut.programmaticSchema.getSchema() >> blankSchema

        where:
        desc             | useIDL
        'use IDL'        | true
        'do not use IDL' | false
    }


}

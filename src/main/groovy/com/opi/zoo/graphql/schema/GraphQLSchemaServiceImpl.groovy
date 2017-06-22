package com.opi.zoo.graphql.schema

import com.opi.zoo.graphql.schema.builder.IDLSchemaBuilder
import com.opi.zoo.graphql.schema.builder.ProgrammaticSchemaBuilder
import graphql.schema.GraphQLSchema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GraphQLSchemaServiceImpl implements GraphQLSchemaService {
    @Value('${graphql.use_idl:true}')
    boolean useIDL

    @Autowired
    IDLSchemaBuilder idlSchema

    @Autowired
    ProgrammaticSchemaBuilder programmaticSchema

    GraphQLSchema getSchema() {
        useIDL ? idlSchema.getSchema() : programmaticSchema.getSchema()
    }
}
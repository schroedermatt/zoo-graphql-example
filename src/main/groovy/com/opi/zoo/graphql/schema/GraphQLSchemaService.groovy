package com.opi.zoo.graphql.schema

import graphql.schema.GraphQLSchema

interface GraphQLSchemaService {
    GraphQLSchema getSchema()
}
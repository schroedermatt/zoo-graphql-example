package com.opi.zoo.graphql

import graphql.schema.GraphQLSchema

interface GraphQLSchemaBuilder {
    GraphQLSchema buildSchema()
}
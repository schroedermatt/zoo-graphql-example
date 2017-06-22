package com.opi.zoo.graphql.schema.builder

import graphql.schema.GraphQLSchema

import javax.annotation.PostConstruct

abstract class AbstractSchemaBuilder {
    GraphQLSchema schema

    @PostConstruct
    abstract void buildSchema()
}
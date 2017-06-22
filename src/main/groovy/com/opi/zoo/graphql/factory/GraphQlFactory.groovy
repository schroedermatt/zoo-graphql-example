package com.opi.zoo.graphql.factory

import graphql.GraphQL

interface GraphQlFactory {
    GraphQL createGraphQL()
}
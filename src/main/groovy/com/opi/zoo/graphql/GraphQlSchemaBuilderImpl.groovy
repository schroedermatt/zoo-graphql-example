package com.opi.zoo.graphql

import com.opi.zoo.rest.domain.Keeper
import com.opi.zoo.rest.repository.AnimalRepository
import com.opi.zoo.rest.repository.KeeperRepository
import graphql.schema.GraphQLList
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static graphql.Scalars.GraphQLLong
import static graphql.Scalars.GraphQLString
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition
import static graphql.schema.GraphQLObjectType.newObject

@Component
@Slf4j
class GraphQlSchemaBuilderImpl implements GraphQLSchemaBuilder {
    @Autowired
    AnimalRepository animalRepository

    @Autowired
    KeeperRepository keeperRepository

    GraphQLSchema buildSchema() {

        GraphQLObjectType animalType = newObject()
                .name("animal")
                .description("A Zoo Animal")
                .field(newFieldDefinition()
                    .name("id")
                    .description("Animal ID")
                    .type(GraphQLLong)
                    .build())

                .field(newFieldDefinition()
                    .name("name")
                    .description("Animal Name")
                    .type(GraphQLString)
                    .build())

                .field(newFieldDefinition()
                    .name("type")
                    .description("Type of Animal")
                    .type(GraphQLString)
                    .build())

                .build()

        GraphQLObjectType keeperType = newObject()
                .name("keeper")
                .description("A Zoo Keeper")
                .field(newFieldDefinition()
                    .name("id")
                    .description("Keeper ID")
                    .type(GraphQLLong)
                    .build())

                .field(newFieldDefinition()
                    .name("name")
                    .description("Keeper Name")
                    .type(GraphQLString)
                    .build())

                .field(newFieldDefinition()
                    .name("animals")
                    .description("Animals that the keeper cares for.")
                    .type(new GraphQLList(animalType))
                    .dataFetcher({ it.<Keeper>getSource().animals })
                    .build())

                .build()

        GraphQLObjectType queryType = newObject()
            .name("query")
            .description("Root Query Type")
            .field(newFieldDefinition()
                .name("keepers")
                .description("Overview of all Zoo Keepers")
                .type(new GraphQLList(keeperType))
                .dataFetcher({ env -> keeperRepository.findAll() })
                .build())
            .field(newFieldDefinition()
                .name("animals")
                .type(new GraphQLList(animalType))
                .dataFetcher({ env -> animalRepository.findAll() })
                .build())
            .build()

        GraphQLSchema
                .newSchema()
                .query(queryType)
                .build()
    }



}

package com.opi.zoo.graphql.schema.builder

import com.opi.zoo.graphql.datafetcher.AnimalDataFetcher
import com.opi.zoo.graphql.datafetcher.KeeperDataFetcher
import com.opi.zoo.rest.domain.Keeper
import graphql.Scalars
import graphql.schema.GraphQLList
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

import static graphql.Scalars.GraphQLLong
import static graphql.Scalars.GraphQLString
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition
import static graphql.schema.GraphQLObjectType.newObject

@Component
class ProgrammaticSchemaBuilder extends AbstractSchemaBuilder {
    @Autowired
    AnimalDataFetcher animalDataFetcher

    @Autowired
    KeeperDataFetcher keeperDataFetcher

    @PostConstruct
    void buildSchema() {
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

            // simple example of a generated value not on the original data model
            .field(newFieldDefinition()
                .name("animalcount")
                .description("Number of animals cared for.")
                .type(Scalars.GraphQLInt)
                .dataFetcher({ it.<Keeper>getSource().animals?.size() })
                .build())

            .field(newFieldDefinition()
                .name("animals")
                .description("Animals that the keeper cares for.")
                .type(new GraphQLList(animalType))
                .build())

            .build()

        GraphQLObjectType queryType = newObject()
            .name("query")
            .description("Root Query Type")
            .field(newFieldDefinition()
                .name("keepers")
                .description("Overview of all Zoo Keepers")
                .type(new GraphQLList(keeperType))
                .dataFetcher({ env -> keeperDataFetcher.findAll() })
                .build())

            .field(newFieldDefinition()
                .name("animals")
                .description("Overview of all animals")
                .type(new GraphQLList(animalType))
                .dataFetcher({ env -> animalDataFetcher.findAll() })
                .build())

            .build()

        this.schema = GraphQLSchema
                .newSchema()
                .query(queryType)
                .build()
    }



}

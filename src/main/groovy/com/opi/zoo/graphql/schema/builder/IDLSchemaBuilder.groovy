package com.opi.zoo.graphql.schema.builder

import com.opi.zoo.graphql.datafetcher.AnimalDataFetcher
import com.opi.zoo.graphql.datafetcher.KeeperDataFetcher
import com.opi.zoo.graphql.schema.helper.ResourceReader
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring

@Component
class IDLSchemaBuilder extends AbstractSchemaBuilder {
    @Autowired
    AnimalDataFetcher animalDataFetcher

    @Autowired
    KeeperDataFetcher keeperDataFetcher

    @Autowired
    ResourceReader resourceReader

    @PostConstruct
    void buildSchema() {
        String schemaFile = resourceReader.getResourceAsString("/schema.graphqls")

        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry()
        typeRegistry.merge(new SchemaParser().parse(schemaFile))

        this.schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, buildRuntimeWiring())
    }

    /**
     * The IDL schema file only contains the type information. The RuntimeWiring is where
     * dataFetchers and other resolvers can be injected into the schema.
     */
    private RuntimeWiring buildRuntimeWiring() {
        def typeWiring = newTypeWiring("QueryType")
            .dataFetcher("keepers", { env -> keeperDataFetcher.filterAll(env) })
            .dataFetcher("animals", { env -> animalDataFetcher.filterAll(env) })
            .build()

        return newRuntimeWiring().type(typeWiring).build()
    }
}

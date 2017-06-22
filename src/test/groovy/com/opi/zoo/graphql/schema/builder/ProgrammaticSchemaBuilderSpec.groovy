package com.opi.zoo.graphql.schema.builder

import com.opi.zoo.graphql.datafetcher.AnimalDataFetcher
import com.opi.zoo.graphql.datafetcher.KeeperDataFetcher
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLList
import graphql.schema.GraphQLObjectType
import spock.lang.Specification

class ProgrammaticSchemaBuilderSpec extends Specification {
    def sut = new ProgrammaticSchemaBuilder(
            keeperDataFetcher: Mock(KeeperDataFetcher),
            animalDataFetcher: Mock(AnimalDataFetcher)
    )

    def 'build schema programmatically'() {
        when:
        sut.buildSchema()

        then: 'assert whatever seems necessary to lock in place'
        with(sut.schema) {
            typeMap.containsKey('query')
            typeMap.containsKey('keeper')
            typeMap.containsKey('animal')

            queryType.name == 'query'

            // keepers stuff
            GraphQLFieldDefinition keepers = queryType.fieldDefinitionsByName['keepers']
            keepers.name == 'keepers'
            keepers.type.class == GraphQLList

            GraphQLObjectType keepersType = keepers.type.getAt('wrappedType')
            keepersType.name == 'keeper'
            keepersType.fieldDefinitionsByName.containsKey('id')
            keepersType.fieldDefinitionsByName.containsKey('name')
            keepersType.fieldDefinitionsByName.containsKey('animalcount')
            keepersType.fieldDefinitionsByName.containsKey('animals')

            // animals stuff
            GraphQLFieldDefinition animals = queryType.fieldDefinitionsByName['animals']
            animals.name == 'animals'
            animals.type.class == GraphQLList

            GraphQLObjectType animalsType = animals.type.getAt('wrappedType')
            animalsType.name == 'animal'
            animalsType.fieldDefinitionsByName.containsKey('id')
            animalsType.fieldDefinitionsByName.containsKey('name')
            animalsType.fieldDefinitionsByName.containsKey('type')
        }
    }
}

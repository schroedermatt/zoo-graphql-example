package com.opi.zoo.graphql.schema.builder

import com.opi.zoo.graphql.datafetcher.AnimalDataFetcher
import com.opi.zoo.graphql.datafetcher.KeeperDataFetcher
import com.opi.zoo.graphql.schema.helper.ResourceReader
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLList
import graphql.schema.GraphQLObjectType
import spock.lang.Specification

class IDLSchemaBuilderSpec extends Specification {
    def sut = new IDLSchemaBuilder(
            keeperDataFetcher: Mock(KeeperDataFetcher),
            animalDataFetcher: Mock(AnimalDataFetcher),
            resourceReader: Mock(ResourceReader)
    )

    def 'build schema with IDL'() {
        when:
        sut.buildSchema()

        then: 'assert whatever seems necessary to lock in place'
        sut.resourceReader.getResourceAsString('/schema.graphqls') >> getTestSchema()

        with(sut.schema) {
            typeMap.containsKey('QueryType')
            typeMap.containsKey('Animal')
            typeMap.containsKey('Keeper')

            queryType.name == 'QueryType'

            // keepers stuff
            GraphQLFieldDefinition keepers = queryType.fieldDefinitionsByName['keepers']
            keepers.name == 'keepers'
            keepers.type.class == GraphQLList

            GraphQLObjectType keepersType = keepers.type.getAt('wrappedType')
            keepersType.name == 'Keeper'
            keepersType.fieldDefinitionsByName.containsKey('id')
            keepersType.fieldDefinitionsByName.containsKey('name')
            keepersType.fieldDefinitionsByName.containsKey('animals')

            // animals stuff
            GraphQLFieldDefinition animals = queryType.fieldDefinitionsByName['animals']
            animals.name == 'animals'
            animals.type.class == GraphQLList

            GraphQLObjectType animalsType = animals.type.getAt('wrappedType')
            animalsType.name == 'Animal'
            animalsType.fieldDefinitionsByName.containsKey('id')
            animalsType.fieldDefinitionsByName.containsKey('name')
            animalsType.fieldDefinitionsByName.containsKey('type')
            animalsType.fieldDefinitionsByName.containsKey('birthdate')
        }
    }

    private String getTestSchema() {
        '''
            schema {
                query: QueryType
            }
            
            type QueryType {
                animals(id: Long, name: String): [Animal]
                keepers(id : Long, name: String) : [Keeper]
            }
            
            type Keeper {
                id: Long!
                name: String!
                animals: [Animal]
            }
            
            type Animal {
                id: Long!
                name: String!
                type: String!
                birthdate: String!
            }
        '''
    }
}

package com.opi.zoo.graphql.datafetcher

import com.opi.zoo.rest.domain.Animal
import com.opi.zoo.rest.repository.AnimalRepository
import graphql.schema.DataFetchingEnvironment
import spock.lang.Specification

class AnimalDataFetcherSpec extends Specification {
    AnimalDataFetcher sut = new AnimalDataFetcher(
        animalRepository: Mock(AnimalRepository)
    )

    def "findAll returns all animals"() {
        given:
        def animals = [new Animal(id: 1L)]

        when:
        def result = sut.findAll()

        then:
        sut.animalRepository.findAll() >> animals
        result == animals
    }

    def "filterAll returns all animals when no args are passed"() {
        given:
        def animals = [new Animal(id: 1L)]
        def env =  Mock(DataFetchingEnvironment)

        when:
        def result = sut.filterAll(env)

        then:
        env.getArgument('id') >> null
        sut.animalRepository.findAll() >> animals
        result == animals
    }

    def "filterAll returns filtered animals when args are passed"() {
        given:
        def idValue = 1L
        def animal = new Animal(id: idValue)
        def env =  Mock(DataFetchingEnvironment)

        when:
        def result = sut.filterAll(env)

        then:
        env.getArgument('id') >> idValue
        sut.animalRepository.findOne(idValue) >> animal
        result == [animal]
    }
}

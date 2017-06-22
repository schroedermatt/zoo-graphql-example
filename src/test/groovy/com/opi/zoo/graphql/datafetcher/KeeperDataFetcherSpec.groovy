package com.opi.zoo.graphql.datafetcher

import com.opi.zoo.rest.domain.Keeper
import com.opi.zoo.rest.repository.KeeperRepository
import graphql.schema.DataFetchingEnvironment
import spock.lang.Specification

class KeeperDataFetcherSpec extends Specification {
    KeeperDataFetcher sut = new KeeperDataFetcher(
            keeperRepository: Mock(KeeperRepository)
    )

    def "findAll returns all keepers"() {
        given:
        def keepers = [new Keeper(id: 1L)]

        when:
        def result = sut.findAll()

        then:
        sut.keeperRepository.findAll() >> keepers
        result == keepers
    }

    def "filterAll returns all keepers when no args are passed"() {
        given:
        def keepers = [new Keeper(id: 1L)]
        def env =  Mock(DataFetchingEnvironment)

        when:
        def result = sut.filterAll(env)

        then:
        env.getArgument('id') >> null
        sut.keeperRepository.findAll() >> keepers
        result == keepers
    }

    def "filterAll returns filtered keepers when args are passed"() {
        given:
        def idValue = 1L
        def keeper = new Keeper(id: idValue)
        def env =  Mock(DataFetchingEnvironment)

        when:
        def result = sut.filterAll(env)

        then:
        env.getArgument('id') >> idValue
        sut.keeperRepository.findOne(idValue) >> keeper
        result == [keeper]
    }
}

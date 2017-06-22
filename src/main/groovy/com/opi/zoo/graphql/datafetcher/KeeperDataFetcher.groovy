package com.opi.zoo.graphql.datafetcher

import com.opi.zoo.rest.domain.Keeper
import com.opi.zoo.rest.repository.KeeperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KeeperDataFetcher {
    // this data source could be swapped out for anything...
    @Autowired
    KeeperRepository keeperRepository

    List<Keeper> findAll() {
        keeperRepository.findAll()
    }
}

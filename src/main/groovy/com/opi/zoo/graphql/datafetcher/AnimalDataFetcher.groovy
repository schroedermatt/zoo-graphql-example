package com.opi.zoo.graphql.datafetcher

import com.opi.zoo.rest.domain.Animal
import com.opi.zoo.rest.repository.AnimalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AnimalDataFetcher {
    // this data source could be swapped out for anything...
    @Autowired
    AnimalRepository animalRepository

    List<Animal> findAll() {
        animalRepository.findAll()
    }
}

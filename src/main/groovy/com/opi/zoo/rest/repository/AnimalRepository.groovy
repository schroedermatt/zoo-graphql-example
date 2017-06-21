package com.opi.zoo.rest.repository

import com.opi.zoo.rest.domain.Animal
import org.springframework.data.jpa.repository.JpaRepository

interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByName(String name)
}

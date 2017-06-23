package com.opi.zoo.rest.controller

import com.opi.zoo.rest.domain.Animal
import com.opi.zoo.rest.repository.AnimalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/animals")
class AnimalController {
    // note: service layer left out for simplicity
    @Autowired
    AnimalRepository animalRepository

    @GetMapping
    ResponseEntity<List<Animal>> findAllAnimals() {
        ResponseEntity.ok(animalRepository.findAll())
    }

    @GetMapping("/{id}")
    ResponseEntity<Animal> getAnimal(@PathVariable Long id) {
        ResponseEntity.ok(animalRepository.findOne(id))
    }

// create not needed for example
//    @PostMapping
//    ResponseEntity createAnimal(@RequestBody Animal animal) {
//        Animal created = animalRepository.save(animal)
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(created.getId()).toUri()
//
//        ResponseEntity.created(location).build()
//    }
}

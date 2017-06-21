package com.opi.zoo.rest.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity
class Animal {
    @Id
    Long id

    @NotNull
    String name

    @NotNull
    AnimalType type

    Instant birthdate

    // todo -> future example
//    @OneToMany
//    List<Animal> children
}

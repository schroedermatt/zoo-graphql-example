package com.opi.zoo.rest.domain

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull

@Entity
class Keeper {
    @Id
    Long id

    @NotNull
    String name

    @OneToMany(fetch = FetchType.EAGER)
    List<Animal> animals
}

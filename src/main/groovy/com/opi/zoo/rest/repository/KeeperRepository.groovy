package com.opi.zoo.rest.repository

import com.opi.zoo.rest.domain.Keeper
import org.springframework.data.jpa.repository.JpaRepository

interface KeeperRepository extends JpaRepository<Keeper, Long> {
    List<Keeper> findByName(String name)
}

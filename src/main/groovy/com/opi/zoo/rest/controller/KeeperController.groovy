package com.opi.zoo.rest.controller

import com.opi.zoo.rest.domain.Keeper
import com.opi.zoo.rest.repository.KeeperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/keepers")
class KeeperController {
    @Autowired
    KeeperRepository keeperRepository

    @GetMapping
    ResponseEntity<List<Keeper>> findAllKeepers() {
        ResponseEntity.ok(keeperRepository.findAll())
    }

    @GetMapping("/{id}")
    ResponseEntity<Keeper> getKeeper(@PathVariable Long id) {
        ResponseEntity.ok(keeperRepository.findOne(id))
    }

// create may not be needed for example
//    @PostMapping
//    ResponseEntity createKeeper(@RequestBody Keeper keeper) {
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(keeper.getId()).toUri()
//
//        ResponseEntity.created(location).build()
//    }
}

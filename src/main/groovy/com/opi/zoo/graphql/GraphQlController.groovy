package com.opi.zoo.graphql

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/graphql")
class GraphQlController {
    @Autowired
    GraphQlExecutor executor

    @PostMapping
    Object executeGraphQl(@RequestBody Map body) {
        executor.executeRequest(body)
    }
}

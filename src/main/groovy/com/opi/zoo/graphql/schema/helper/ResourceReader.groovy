package com.opi.zoo.graphql.schema.helper

import org.springframework.stereotype.Component

/**
 * Little helper to abstract out reading a resource file
 */
@Component
class ResourceReader {
    String getResourceAsString(String filename) {
        this.class.getResourceAsStream(filename).text ?: ''
    }
}

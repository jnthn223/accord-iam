package com.jnthn.accord_iam.playground.resource.web

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/resource/playground")
class PlaygroundResourceController {

    @GetMapping("/profile")
    fun playgroundProfile(@AuthenticationPrincipal jwt: Jwt): Map<String, Any> {

        if (jwt.getClaim<Boolean>("playground") != true) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN)
        }

        return mapOf(
            "message" to "Playground resource accessed",
            "client_id" to jwt.getClaimAsString("client_id"),
            "project_id" to jwt.getClaimAsString("project_id")
        )
    }
}
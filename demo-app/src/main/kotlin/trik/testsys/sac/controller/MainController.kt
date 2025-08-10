package trik.testsys.sac.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.controller.response.ResponseData

@RestController
@RequestMapping("/api")
class MainController {

    @GetMapping("/hello")
    fun hello(authentication: Authentication): ResponseEntity<HelloResponse> {
        val userName = authentication.name
        return ResponseEntity.ok(
            HelloResponse(message = "Hello, authenticated user $userName")
        )
    }

    @GetMapping("/me")
    fun me(authentication: Authentication): ResponseEntity<MeResponse> {
        val authorities = authentication.authorities.map { it.authority }.sorted()
        return ResponseEntity.ok(
            MeResponse(
                username = authentication.name,
                authorities = authorities
            )
        )
    }

    data class HelloResponse(
        val message: String
    ) : ResponseData

    data class MeResponse(
        val username: String,
        val authorities: List<String>
    ) : ResponseData
}



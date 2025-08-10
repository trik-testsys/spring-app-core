package trik.testsys.sac.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.controller.response.ResponseData

@RestController
@RequestMapping("/api/secure")
class SecureDemoController {

    @GetMapping("/hello")
    fun helloSecure(authentication: Authentication): ResponseEntity<SecureHelloResponse> {
        val jwtAuth = authentication as JwtAuthenticationToken

        val body = SecureHelloResponse(
            message = "Hello, ${jwtAuth.name}",
            subject = jwtAuth.name,
            authorities = jwtAuth.authorities.map { it.authority },
            issuer = jwtAuth.token.issuer.toString(),
            issuedAt = jwtAuth.token.issuedAt?.toString(),
            expiresAt = jwtAuth.token.expiresAt?.toString()
        )
        return ResponseEntity.ok(body)
    }

    @GetMapping("/me")
    fun me(authentication: Authentication): ResponseEntity<SecureMeResponse> {
        val jwtAuth = authentication as JwtAuthenticationToken

        val body = SecureMeResponse(
            subject = jwtAuth.name,
            authorities = jwtAuth.authorities.map { it.authority },
            issuedAt = jwtAuth.token.issuedAt?.toString(),
            expiresAt = jwtAuth.token.expiresAt?.toString()
        )
        return ResponseEntity.ok(body)
    }

    data class SecureHelloResponse(
        val message: String,
        val subject: String,
        val authorities: List<String>,
        val issuer: String?,
        val issuedAt: String?,
        val expiresAt: String?
    ) : ResponseData

    data class SecureMeResponse(
        val subject: String,
        val authorities: List<String>,
        val issuedAt: String?,
        val expiresAt: String?
    ) : ResponseData
}



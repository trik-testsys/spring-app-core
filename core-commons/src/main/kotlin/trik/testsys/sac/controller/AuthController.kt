package trik.testsys.sac.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ResponseStatusException
import trik.testsys.sac.config.security.JwtSecurityConfig.Companion.API_PERMIT_ALL_PATH
import trik.testsys.sac.controller.response.ResponseData
import trik.testsys.sac.service.TokenClaimsService
import java.time.Instant
import java.time.temporal.ChronoUnit

@RequestMapping("$API_PERMIT_ALL_PATH/auth")
class AuthController(
    private val jwtEncoder: JwtEncoder,
    private val tokenClaimsService: TokenClaimsService
) {

    data class TokenRequest(
        val accessToken: String,
        val expiresInSeconds: Long? = 3600
    )

    data class TokenResponse(
        val token: String,
        val tokenType: String = "Bearer",
        val expiresAt: Long
    ) : ResponseData

    @PostMapping("/token")
    @ResponseBody
    fun issueToken(@RequestBody request: TokenRequest): ResponseEntity<TokenResponse> {
        val user = tokenClaimsService.resolve(request.accessToken)
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid access token")

        val now = Instant.now()
        val expiresAt = now.plus(request.expiresInSeconds ?: 3600, ChronoUnit.SECONDS)

        val claims = JwtClaimsSet.builder()
            .subject(user.userId?.toString() ?: user.name)
            .issuedAt(now)
            .expiresAt(expiresAt)
            .claim("userId", user.userId)
            .claim("name", user.name)
            .claim("privileges", user.privileges)
            .build()

        val headers = JwsHeader.with(MacAlgorithm.HS256).build()
        val jwt = jwtEncoder.encode(JwtEncoderParameters.from(headers, claims))
        val response = TokenResponse(
            token = jwt.tokenValue,
            expiresAt = expiresAt.epochSecond
        )
        return ResponseEntity.ok(response)
    }
}



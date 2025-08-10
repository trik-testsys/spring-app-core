package trik.testsys.sac.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.web.AuthenticationEntryPoint
import trik.testsys.sac.service.TokenClaimsService
import java.time.Instant
import java.time.temporal.ChronoUnit

class TokenRefreshEntryPoint(
    private val jwtEncoder: JwtEncoder,
    private val tokenClaimsService: TokenClaimsService
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val accessToken = request.getHeader(ACCESS_TOKEN_HEADER) ?: request.getParameter(ACCESS_TOKEN_PARAM)

        if (!accessToken.isNullOrBlank()) {
            val user = tokenClaimsService.resolve(accessToken)
            if (user != null) {
                val now = Instant.now()
                val expiresAt = now.plus(DEFAULT_EXPIRES_IN_SECONDS, ChronoUnit.SECONDS)

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

                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                response.writer.use { out ->
                    out.write("""
                        {"token":"${jwt.tokenValue}","tokenType":"Bearer","expiresAt":${expiresAt.epochSecond},"reason":"expired_or_invalid"}
                    """.trimIndent())
                }
                return
            }
        }

        // Default: send 401 without token
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.message)
    }

    companion object {
        private const val ACCESS_TOKEN_HEADER = "X-Access-Token"
        private const val ACCESS_TOKEN_PARAM = "accessToken"
        private const val DEFAULT_EXPIRES_IN_SECONDS = 3600L
    }
}



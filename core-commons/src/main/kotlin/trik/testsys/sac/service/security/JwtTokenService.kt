package trik.testsys.sac.service.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import trik.testsys.sac.data.entity.user.AbstractUserEntity
import java.time.Instant

/**
 * Service responsible for generating signed JWTs and building authenticated tokens for users.
 *
 * Uses [JwtEncoder] configured in the application context. Claims include issuer, subject, issued/expiry times,
 * basic user information and the user's privilege codes under the `privileges` claim which is consumed by
 * [UserPrivilegesAuthoritiesConverter].
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@Service
class JwtTokenService(
    private val jwtEncoder: JwtEncoder,
    private val jwtAuthenticationConverter: JwtAuthenticationConverter,

    @Value("\${sac.security.jwt.issuer}")
    private val issuer: String,
    @Value("\${sac.security.jwt.ttl-seconds}")
    private val ttlSeconds: Long
) {

    /**
     * Generates a signed [Jwt] for the given [user].
     */
    @Transactional(readOnly = true)
    fun generateJwtForUser(user: AbstractUserEntity): Jwt {
        val now = Instant.now()
        val expiresAt = now.plusSeconds(ttlSeconds)

        val claims = JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(expiresAt)
            .subject(user.name)
            .claim(UID, user.id)
            .claim(NAME, user.name)
            .claim(PRIVILEGES, user.privilegeCodes.toList())
            .build()

        val headers = JwsHeader.with(MacAlgorithm.HS256).build()
        return jwtEncoder.encode(JwtEncoderParameters.from(headers, claims))
    }

    /**
     * Builds an authenticated [JwtAuthenticationToken] for the given [user].
     */
    @Transactional(readOnly = true)
    fun buildAuthenticationForUser(user: AbstractUserEntity): JwtAuthenticationToken {
        val jwt = generateJwtForUser(user)
        val authentication = jwtAuthenticationConverter.convert(jwt)
            ?: error("Failed to convert JWT to Authentication")
        return authentication as JwtAuthenticationToken
    }

    companion object {

        const val UID = "uid"
        const val NAME = "name"
        const val PRIVILEGES = "privileges"
    }
}



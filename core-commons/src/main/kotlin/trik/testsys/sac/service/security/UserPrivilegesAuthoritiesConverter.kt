package trik.testsys.sac.service.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Service
import trik.testsys.sac.service.security.JwtTokenService.Companion.PRIVILEGES

/**
 * Converter that maps a JWT into a collection of [GrantedAuthority].
 *
 * Uses the default [JwtGrantedAuthoritiesConverter] and augments it with values from a custom
 * claim (by default `privileges`).
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@Service
class UserPrivilegesAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Delegate converter that extracts scope/authority claims according to Spring defaults.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    private val delegate = JwtGrantedAuthoritiesConverter()

    /**
     * Converts the given [Jwt] into a collection of [GrantedAuthority], including custom privileges.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        val authorities = delegate.convert(jwt)?.toMutableSet() ?: mutableSetOf()
        val privileges = jwt.getClaimAsStringList(PRIVILEGES) ?: emptyList()
        authorities += privileges.map { SimpleGrantedAuthority(it) }
        return authorities
    }
}



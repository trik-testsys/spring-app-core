package trik.testsys.sac.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.stereotype.Component

/**
 * Converter that maps a JWT into a collection of [GrantedAuthority].
 *
 * Uses the default [JwtGrantedAuthoritiesConverter] and augments it with values from a custom
 * claim (by default `privileges`).
 */
@Component
class UserPrivilegesAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {

    private val delegate = JwtGrantedAuthoritiesConverter()

    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        val authorities = delegate.convert(jwt)?.toMutableSet() ?: mutableSetOf()
        val privileges = jwt.getClaimAsStringList(CUSTOM_CLAIM_NAME) ?: emptyList()
        authorities += privileges.map { SimpleGrantedAuthority(it) }
        return authorities
    }

    companion object {

        private const val CUSTOM_CLAIM_NAME = "privileges"
    }
}



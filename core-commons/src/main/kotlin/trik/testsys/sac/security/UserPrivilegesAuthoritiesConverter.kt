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
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@Component
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
        val privileges = jwt.getClaimAsStringList(CUSTOM_CLAIM_NAME) ?: emptyList()
        authorities += privileges.map { SimpleGrantedAuthority(it) }
        return authorities
    }

    companion object {

        /**
         * Name of the custom claim containing application-specific privileges.
         *
         * @author Roman Shishkin
         * @since 1.1.0
         */
        private const val CUSTOM_CLAIM_NAME = "privileges"
    }
}



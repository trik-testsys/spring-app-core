package trik.testsys.sac.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import trik.testsys.sac.security.UserPrivilegesAuthoritiesConverter

/**
 * Common security beans to wire JWT converters.
 *
 * @property userPrivilegesAuthoritiesConverter Converter that extracts custom authorities from JWTs.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@Configuration
class SecurityBeansConfig(
    private val userPrivilegesAuthoritiesConverter: UserPrivilegesAuthoritiesConverter
) {

    /**
     * Creates a [JwtAuthenticationConverter] that delegates authority extraction to
     * [UserPrivilegesAuthoritiesConverter].
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter = JwtAuthenticationConverter().apply {
        setJwtGrantedAuthoritiesConverter(userPrivilegesAuthoritiesConverter)
    }
}



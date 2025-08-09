package trik.testsys.sac.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import trik.testsys.sac.security.UserPrivilegesAuthoritiesConverter

/**
 * Common security beans to wire JWT converters.
 */
@Configuration
class SecurityBeansConfig(
    private val userPrivilegesAuthoritiesConverter: UserPrivilegesAuthoritiesConverter
) {

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter = JwtAuthenticationConverter().apply {
        setJwtGrantedAuthoritiesConverter(userPrivilegesAuthoritiesConverter)
    }
}



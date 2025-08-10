package trik.testsys.sac.config.security

import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import trik.testsys.sac.security.UserPrivilegesAuthoritiesConverter
import trik.testsys.sac.service.TokenClaimsService

/**
 * Common security beans to wire JWT converters.
 *
 * @property userPrivilegesAuthoritiesConverter Converter that extracts custom authorities from JWTs.
 *
 * @author Roman Shishkin
 * @since 1.1.0
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
     * @since 1.1.0
     */
    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter = JwtAuthenticationConverter().apply {
        setJwtGrantedAuthoritiesConverter(userPrivilegesAuthoritiesConverter)
    }

    @Bean
    fun tokenRefreshEntryPoint(
        jwtEncoder: ObjectProvider<org.springframework.security.oauth2.jwt.JwtEncoder>,
        tokenClaimsService: ObjectProvider<TokenClaimsService>
    ): TokenRefreshEntryPoint? {
        val encoder = jwtEncoder.ifAvailable
        val claimsService = tokenClaimsService.ifAvailable
        return if (encoder != null && claimsService != null) TokenRefreshEntryPoint(encoder, claimsService) else null
    }
}



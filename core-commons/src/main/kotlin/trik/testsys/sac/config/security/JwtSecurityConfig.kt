package trik.testsys.sac.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import trik.testsys.sac.service.security.AccessTokenAuthenticationFilter

/**
 * Minimal JWT-based security configuration for resource servers.
 *
 * Applications should provide a valid [com.nimbusds.jose.jwk.JWKSet] via configuration or override the [jwtDecoder] bean
 * to point to their authorization server. This core config sets up stateless JWT authentication
 * and leaves endpoint authorization to the consumer application.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@Configuration
@EnableWebSecurity
class JwtSecurityConfig {

    /**
     * Builds the Spring Security filter chain with stateless JWT authentication.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationConverter: JwtAuthenticationConverter,
        accessTokenAuthenticationFilter: AccessTokenAuthenticationFilter
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(API_PERMIT_ALL_PATTERN, ACTUATOR_HEALTH_PATTERN).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt { jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter) } }
            .addFilterBefore(accessTokenAuthenticationFilter, BearerTokenAuthenticationFilter::class.java)

        return http.build()
    }

    companion object {

        const val API_PERMIT_ALL_PATH = "/api/permit-all"
        const val API_PERMIT_ALL_PATTERN = "$API_PERMIT_ALL_PATH/**"
        const val ACTUATOR_HEALTH_PATTERN = "/actuator/health"
    }
}



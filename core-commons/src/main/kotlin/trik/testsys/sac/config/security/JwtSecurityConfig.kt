package trik.testsys.sac.config.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import trik.testsys.sac.service.security.AccessTokenAuthenticationFilter
import trik.testsys.sac.service.security.AuthorityProvider

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
class JwtSecurityConfig(
    context: ApplicationContext
) {

    private val authorityProviders = context.getBeansOfType<AuthorityProvider>()

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
                    .fillAuthorities()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt { jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter) } }
            .addFilterBefore(accessTokenAuthenticationFilter, BearerTokenAuthenticationFilter::class.java)

        return http.build()
    }

    private fun AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry.fillAuthorities(): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
        logger.info("Found ${authorityProviders.size} authority providers. Executing each other.")

        authorityProviders.forEach { (name, provider) ->
            logger.info("Executing $name provider.")

            val patterns = provider.patterns.toList()
            val authorities = provider.authorities.map { it.authority }

            if (patterns.isNotEmpty() && authorities.isNotEmpty()) {
                this.requestMatchers(*patterns.toTypedArray()).hasAnyAuthority(*authorities.toTypedArray())
            } else {
                logger.info("Provider $name skipped: patterns=${patterns.size}, authorities=${authorities.size}")
            }
        }

        return this
    }

    companion object {

        private val logger = LoggerFactory.getLogger(JwtSecurityConfig::class.java)

        const val API_PERMIT_ALL_PATH = "/api/permit-all"
        const val API_PERMIT_ALL_PATTERN = "$API_PERMIT_ALL_PATH/**"
        const val ACTUATOR_HEALTH_PATTERN = "/actuator/health"
    }
}



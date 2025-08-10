package trik.testsys.sac.config.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import javax.crypto.spec.SecretKeySpec

/**
 * Provides a default [JwtDecoder] for applications that do not define their own.
 *
 * The default implementation uses an HS256 symmetric key derived from the property
 * `sac.security.jwt.secret`. It is intended for development/local environments.
 * Production services should supply their own [JwtDecoder] (e.g. via
 * `spring.security.oauth2.resourceserver.jwt.issuer-uri`/`jwk-set-uri`) or
 * override this bean with a stronger configuration.
 *
 * This bean is only created when there is no other [JwtDecoder] in the context.
 */
@Configuration
class DefaultJwtConfig {

    @Bean
    @ConditionalOnMissingBean(JwtDecoder::class)
    fun jwtDecoder(
        @Value("\${sac.security.jwt.secret}")
        secret: String
    ): JwtDecoder {
        if (secret == DEFAULT_INSECURE_SECRET) logger.error("JWT Secret is insecure, only for dev usage.\n\n !!!NOT FOR PRODUCTION!!!\n")

        val key = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        return NimbusJwtDecoder.withSecretKey(key).build()
    }

    @Bean
    @ConditionalOnMissingBean(JwtEncoder::class)
    fun jwtEncoder(
        @Value("\${sac.security.jwt.secret}")
        secret: String
    ): JwtEncoder {
        val jwkSource = ImmutableSecret<SecurityContext>(secret.toByteArray())
        return NimbusJwtEncoder(jwkSource)
    }

    companion object {

        private val logger = LoggerFactory.getLogger(DefaultJwtConfig::class.java)

        private const val DEFAULT_INSECURE_SECRET = "default-insecure-secret"
    }
}



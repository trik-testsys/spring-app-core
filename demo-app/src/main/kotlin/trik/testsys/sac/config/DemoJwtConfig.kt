package trik.testsys.sac.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import javax.crypto.spec.SecretKeySpec

@Configuration
class DemoJwtConfig {

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secret = "demo-demo-demo-demo-demo-demo-demo-256"
        val key = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        return NimbusJwtDecoder.withSecretKey(key).build()
    }
}
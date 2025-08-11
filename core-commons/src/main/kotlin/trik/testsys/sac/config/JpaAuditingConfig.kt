package trik.testsys.sac.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.EnableTransactionManagement
import trik.testsys.sac.data.entity.user.SystemUser
import java.util.Optional

/**
 * Configuration enabling Spring Data JPA auditing.
 *
 * With this enabled, fields annotated with `@CreatedDate` (and others like `@LastModifiedDate`
 * if used) are populated automatically by the persistence layer.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@Configuration
@EnableJpaAuditing(modifyOnCreate = true)
@EnableTransactionManagement
@EntityScan(basePackages = ["trik.testsys.**.entity"])
@EnableJpaRepositories(basePackages = ["trik.testsys.**.repository"])
class JpaAuditingConfig {

    /**
     * Auditor provider that resolves the current authenticated user from Spring Security.
     *
     * Only authenticated, non-anonymous users are considered auditors. If no such user is
     * present, the auditor is absent and `createdBy`/`lastModifiedBy` remain null.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    @Bean
    fun auditorAware(): AuditorAware<Long> = AuditorAware {
        val authentication = SecurityContextHolder.getContext()?.authentication
        val auditorId: Long? = when {
            authentication == null
                || !authentication.isAuthenticated
                || authentication is AnonymousAuthenticationToken -> null
            authentication is JwtAuthenticationToken -> (authentication.token.claims["uid"] as? Number)?.toLong()
            else -> authentication.name?.toLongOrNull()
        }

        Optional.ofNullable(auditorId ?: SystemUser.id)
    }
}

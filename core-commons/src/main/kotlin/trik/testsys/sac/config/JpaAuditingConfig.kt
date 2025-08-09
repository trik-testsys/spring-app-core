package trik.testsys.sac.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.domain.AuditorAware
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

/**
 * Configuration enabling Spring Data JPA auditing.
 *
 * With this enabled, fields annotated with `@CreatedDate` (and others like `@LastModifiedDate`
 * if used) are populated automatically by the persistence layer.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@Configuration
@EnableJpaAuditing
class JpaAuditingConfig {

    /**
     * Auditor provider that resolves the current authenticated user from Spring Security.
     *
     * Only authenticated, non-anonymous users are considered auditors. If no such user is
     * present, the auditor is absent and `createdBy`/`lastModifiedBy` remain null.
     */
    @Bean
    fun auditorAware(): AuditorAware<String> = AuditorAware {
        val authentication = SecurityContextHolder.getContext()?.authentication
        if (authentication == null || !authentication.isAuthenticated || authentication is AnonymousAuthenticationToken) {
            Optional.empty()
        } else {
            Optional.ofNullable(authentication.name)
        }
    }
}

package trik.testsys.sac.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

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
class JpaAuditingConfig

package trik.testsys.sac.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

/**
 * Enables Spring Data JPA auditing for automatic population of audit fields
 * such as CreatedDate.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@Configuration
@EnableJpaAuditing
class JpaAuditingConfig

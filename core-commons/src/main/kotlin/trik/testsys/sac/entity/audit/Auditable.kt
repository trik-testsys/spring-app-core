package trik.testsys.sac.entity.audit

import java.time.Instant

/**
 * Contract for auditable domain objects providing created/modified metadata.
 *
 * Implemented by [AuditableEntity] and can be implemented by custom entities if needed.
 *
 * @property createdBy Identifier of the creator (e.g., username or user id)
 * @property lastModifiedAt Last modification timestamp in UTC
 * @property lastModifiedBy Identifier of the last modifier (e.g., username or user id)
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface Auditable {
    val createdBy: String?
    val lastModifiedAt: Instant?
    val lastModifiedBy: String?
}



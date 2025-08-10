package trik.testsys.sac.data.entity.audit

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

    /**
     * Identifier of the creator.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    val createdBy: String?

    /**
     * Last modification timestamp in UTC.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    val lastModifiedAt: Instant?

    /**
     * Identifier of the last modifier.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    val lastModifiedBy: String?
}



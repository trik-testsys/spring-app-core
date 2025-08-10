package trik.testsys.sac.data.entity.audit

import java.time.Instant

/**
 * Contract for auditable domain objects providing created/modified metadata.
 *
 * Implemented by [AbstractAuditableEntity] and can be implemented by custom entities if needed.
 *
 * @property createdBy Identifier of the creator (e.g., username or user id)
 * @property lastModifiedAt Last modification timestamp in UTC
 * @property lastModifiedBy Identifier of the last modifier (e.g., username or user id)
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
interface Auditable {

    /**
     * Identifier of the creator.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    val createdBy: String?

    /**
     * Last modification timestamp in UTC.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    val lastModifiedAt: Instant?

    /**
     * Identifier of the last modifier.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    val lastModifiedBy: String?
}



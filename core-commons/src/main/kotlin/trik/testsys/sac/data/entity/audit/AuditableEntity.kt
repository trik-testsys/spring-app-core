package trik.testsys.sac.data.entity.audit

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import trik.testsys.sac.data.entity.BaseEntity
import java.time.Instant

/**
 * Mapped superclass that adds audit metadata on top of [BaseEntity].
 *
 * Requires Spring Data JPA auditing to be enabled (see `JpaAuditingConfig`).
 * Implement [org.springframework.data.domain.AuditorAware] in the application
 * to provide the current auditor (e.g., username) for `@CreatedBy`/`@LastModifiedBy`.
 *
 * @property createdBy Identifier of the creator
 * @property lastModifiedAt Last modification timestamp in UTC
 * @property lastModifiedBy Identifier of the last modifier
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditableEntity : BaseEntity(), Auditable {

    @CreatedBy
    @Column(name = "created_by", updatable = false, length = 255)
    override var createdBy: String? = null

    @LastModifiedDate
    @Column(name = "last_modified_at")
    override var lastModifiedAt: Instant? = null

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 255)
    override var lastModifiedBy: String? = null
}



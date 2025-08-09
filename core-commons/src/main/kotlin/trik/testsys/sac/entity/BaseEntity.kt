package trik.testsys.sac.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

/**
 * Common base for all JPA entities in the system.
 *
 * - [id]: database primary key
 * - [createdAt]: creation date-time (UTC), filled automatically by JPA auditing
 * - [info]: optional free-form field for additional metadata/notes
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private var id: Long? = null

    fun setId(id: Long?) {
        this.id = id
    }

    override fun getId() = id

    override fun isNew() = id == null

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: Instant

    @Column(name = "info", columnDefinition = "TEXT")
    var info: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as BaseEntity
        if (id == null || other.id == null) return false
        return id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: System.identityHashCode(this)

    companion object {

        const val TABLE_PREFIX = "ts"
    }
}



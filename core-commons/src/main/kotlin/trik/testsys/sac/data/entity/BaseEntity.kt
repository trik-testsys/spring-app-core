package trik.testsys.sac.data.entity

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
 * Base mapped superclass for all JPA entities.
 *
 * Provides a generated primary key, automatic creation timestamp via Spring Data JPA auditing,
 * and a free-form text field for arbitrary metadata. Implements [Persistable] to ensure
 * correct new/managed state detection by Spring Data.
 *
 * Equality is based on persistent identity: two entities are equal if and only if both have
 * non-null identifiers that are equal. Transient (unsaved) instances are never equal.
 *
 * @property id database primary key (generated)
 * @property createdAt entity creation date-time in UTC, set by auditing
 * @property info optional free-form text for notes or metadata
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity : Persistable<Long> {

    /**
     * Database primary key.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private var id: Long? = null

    /**
     * Setter for testing and framework usage.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun setId(id: Long?) {
        this.id = id
    }

    override fun getId() = id

    override fun isNew() = id == null

    /**
     * Entity creation date-time in UTC, set by auditing.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: Instant

    /**
     * Optional free-form text for notes or metadata.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
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

        /**
         * Default database table prefix for entities.
         *
         * @author Roman Shishkin
         * @since 1.1.0
         */
        const val TABLE_PREFIX = "ts_"
    }
}



package trik.testsys.sac.entity.user

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.MappedSuperclass
import trik.testsys.sac.entity.BaseEntity
import java.time.Instant

/**
 * Base mapped superclass for the user domain model.
 *
 * Designed to be extended by child projects which provide the concrete `@Entity`
 * and `@Table` mapping. This class contains common user attributes and persistence
 * conventions used across projects.
 *
 * - Privileges are stored as a set of stable string codes in [privilegeCodes].
 *   Consuming applications should define an enum implementing
 *   `trik.testsys.sac.entity.security.Privilege` and convert to/from these codes.
 * - The collection of privileges is persisted via `@ElementCollection` in a table
 *   named `${TABLE_PREFIX}_privileges` joined by `user_id`.
 *
 * @property accessToken Last known access token used to authorize in the system.
 * Must be unique per user. Max length is [ACCESS_TOKEN_MAX_LENGTH].
 * @property name User's display name. Max length is [NAME_MAX_LENGTH].
 * @property lastLoginAt Last successful login timestamp in UTC, if available.
 * @property hasLoggedIn Convenience flag reflecting whether [lastLoginAt] is non-null.
 * @property privilegeCodes Set of privilege codes assigned to the user.
 *   Codes should correspond to an enum implementing `Privilege` in the consuming app.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@MappedSuperclass
abstract class UserEntity(
    @Column(name = "access_token", length = ACCESS_TOKEN_MAX_LENGTH, unique = true)
    var accessToken: String,
    @Column(name = "name", nullable = false, length = NAME_MAX_LENGTH)
    var name: String
) : BaseEntity() {

    @Column(name = "last_login_at")
    var lastLoginAt: Instant? = null

    @Column(name = "has_logged_in")
    val hasLoggedIn = lastLoginAt != null

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "${TABLE_PREFIX}_privileges", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "privilege_code", nullable = false, length = 128)
    var privilegeCodes: MutableSet<String> = mutableSetOf()

    companion object {

        const val ACCESS_TOKEN_MAX_LENGTH = 255
        const val NAME_MAX_LENGTH = 255
    }
}



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
 * Core user entity.
 *
 * - [accessToken]: last known access token used to authorize in the system
 * - [name]: user's display name
 * - [lastLoginAt]: last login timestamp (UTC)
 * - [privilegeCodes]: set of privilege codes. Concrete projects should provide
 *   an enum implementing `trik.testsys.sac.entity.security.Privilege` with matching codes.
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



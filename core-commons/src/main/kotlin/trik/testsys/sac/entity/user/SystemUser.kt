package trik.testsys.sac.entity.user

/**
 * Singleton system user with all privileges.
 *
 * This object is intended for internal/system operations and is not persisted.
 * It carries a constant access token and name, and is granted all privileges via
 * the wildcard code.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
object SystemUser : UserEntity(
    accessToken = "SYSTEM",
    name = "System"
) {

    init {
        privilegeCodes.add(UserPrivilege.WILDCARD)
    }
}



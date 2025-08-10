package trik.testsys.sac.data.entity.user

/**
 * System user with all privileges.
 *
 * This object is intended for internal/system operations and is not persisted.
 * It carries a constant access token and name, and is granted all privileges via
 * the wildcard code.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
object SystemUser : AbstractUserEntity(
    accessToken = "SYSTEM",
    name = "System"
) {

    init {
        privilegeCodes.add(UserPrivilege.WILDCARD)
    }
}



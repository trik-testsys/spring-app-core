package trik.testsys.sac.entity.security

import trik.testsys.sac.utils.enums.Enum

/**
 * Marker interface for project-specific privilege enums.
 * Implement this in the consuming application and ensure `code` matches
 * what is stored in `User.privilegeCodes`.
 *
 * Example:
 * enum class UserPrivilege(override val code: String) : Privilege { READ("READ"), WRITE("WRITE") }
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface Privilege : Enum {
    val code: String
}



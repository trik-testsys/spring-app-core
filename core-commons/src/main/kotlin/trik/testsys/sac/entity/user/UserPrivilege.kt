package trik.testsys.sac.entity.user

import trik.testsys.sac.utils.enums.Enum

/**
 * Marker interface for project-specific privilege enums.
 *
 * Applications consuming this core module should declare an enum implementing this interface
 * and expose a stable [code] that is stored in the user entity's privilege collection.
 *
 * Example:
 * `enum class UserPrivilege(override val code: String) : Privilege { READ("READ"), WRITE("WRITE") }`
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface UserPrivilege : Enum {

    /**
     * Stable code for the privilege used in persistence and authorization checks.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    val code: String

    companion object {

        /**
         * Special code representing all privileges granted.
         *
         * @author Roman Shishkin
         * @since %CURRENT_VERSION%
         */
        const val WILDCARD: String = "*"
    }
}
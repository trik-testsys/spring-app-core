package trik.testsys.sac.data.service.user

import trik.testsys.sac.data.entity.user.UserEntity
import trik.testsys.sac.data.service.BaseService

/**
 * Service contract focused on user entities extending [UserEntity].
 *
 * Adds user-specific lookup operations on top of the generic [BaseService] API.
 *
 * Type parameters:
 * - [T]: concrete user entity type extending [UserEntity]
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
interface UserService<T : UserEntity> : BaseService<T> {

    /**
     * Returns a user by exact access token or null if not found.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun findByAccessToken(accessToken: String): T?

    /**
     * Returns users by case-insensitive name match.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun findByNameIgnoreCase(name: String): List<T>

    /**
     * Returns users that have at least one of the given privilege codes.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun findAllByAnyPrivilegeIn(codes: Collection<String>): List<T>
}



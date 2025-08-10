package trik.testsys.sac.data.repository.user

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param
import trik.testsys.sac.data.entity.BaseEntity.Companion.TABLE_PREFIX
import trik.testsys.sac.data.entity.user.UserEntity
import trik.testsys.sac.data.repository.BaseRepository

/**
 * Repository contract for accessing user entities that extend [UserEntity].
 *
 * This interface purposefully avoids binding to a specific `@Entity` so that
 * downstream applications can expose their concrete implementation while
 * reusing the common repository API.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@NoRepositoryBean
interface UserRepository<T : UserEntity> : BaseRepository<T> {

    /**
     * Finds a user by an exact access token match.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    fun findByAccessToken(accessToken: String): T?

    /**
     * Case-insensitive search by user name.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    fun findByNameIgnoreCase(name: String): List<T>

    /**
     * Returns all users that have at least one of the given privilege codes.
     *
     * @author Roman Shishkin
     * @since %CURRENT_VERSION%
     */
    @Query(
        """
        select distinct u from ${TABLE_PREFIX}#{#entityName} u
        join u.privilegeCodes pc
        where pc in :codes
        """
    )
    fun findAllByAnyPrivilegeIn(@Param("codes") codes: Collection<String>): List<T>
}



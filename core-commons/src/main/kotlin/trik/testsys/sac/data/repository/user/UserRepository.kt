package trik.testsys.sac.data.repository.user

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.Param
import trik.testsys.sac.data.entity.user.AbstractUserEntity
import trik.testsys.sac.data.repository.EntityRepository

/**
 * Repository contract for accessing user entities that extend [AbstractUserEntity].
 *
 * This interface purposefully avoids binding to a specific `@Entity` so that
 * downstream applications can expose their concrete implementation while
 * reusing the common repository API.
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@NoRepositoryBean
interface UserRepository<T : AbstractUserEntity> : EntityRepository<T> {

    /**
     * Finds a user by an exact access token match.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun findByAccessToken(accessToken: String): T?

    /**
     * Case-insensitive search by user name.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    fun findByNameIgnoreCase(name: String): List<T>

    /**
     * Returns all users that have at least one of the given privilege codes.
     *
     * @author Roman Shishkin
     * @since 1.1.0
     */
    @Query(
        """
        select distinct u from #{#entityName} u
        join u.privilegeCodes pc
        where pc in :codes
        """
    )
    fun findAllByAnyPrivilegeIn(@Param("codes") codes: Collection<String>): List<T>
}



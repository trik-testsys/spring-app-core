package trik.testsys.sac.data.service.user

import org.springframework.transaction.annotation.Transactional
import trik.testsys.sac.data.entity.user.AbstractUserEntity
import trik.testsys.sac.data.repository.user.UserRepository
import trik.testsys.sac.data.service.AbstractService

/**
 * Abstract base implementation of [UserService] backed by a [UserRepository].
 *
 * Provides user-specific read operations and inherits generic CRUD behavior
 * from [AbstractService].
 *
 * Type parameters:
 * - [E]: user entity type extending [AbstractUserEntity]
 * - [R]: repository type extending [UserRepository] for [E]
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@Transactional(readOnly = true)
abstract class AbstractUserService<E, R> : AbstractService<E, R>(), UserService<E>
        where E : AbstractUserEntity,
              R : UserRepository<E> {

    override fun findByAccessToken(accessToken: String): E? =
        repository.findByAccessToken(accessToken)

    override fun findByNameIgnoreCase(name: String): List<E> =
        repository.findByNameIgnoreCase(name)

    override fun findAllByAnyPrivilegeIn(codes: Collection<String>): List<E> =
        repository.findAllByAnyPrivilegeIn(codes)
}
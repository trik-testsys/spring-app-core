@file:Suppress("unused")

package trik.testsys.sac.data.repository.support

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import jakarta.persistence.EntityManager
import trik.testsys.sac.data.entity.BaseEntity

/**
 * Optional repository helpers to ease pagination and specification composition.
 *
 * This provides a base class compatible with Spring Data that projects can reuse
 * if they need to implement custom shared behavior. It's safe to keep unused.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
class ExtendedJpaRepository<T : BaseEntity>(
    domainClass: Class<T>,
    entityManager: EntityManager
) : SimpleJpaRepository<T, Long>(domainClass, entityManager) {

    override fun findAll(specification: Specification<T>?, pageable: Pageable): Page<T> =
        super.findAll(specification, pageable)
}



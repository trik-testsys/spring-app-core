package trik.testsys.sac.data.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import trik.testsys.sac.data.entity.BaseEntity
import trik.testsys.sac.data.repository.BaseRepository

/**
 * Abstract base implementation of [BaseService] backed by a Spring Data [BaseRepository].
 *
 * - Declares common CRUD and query operations with read-only transactions by default
 * - Wires the concrete repository via Spring's dependency injection
 * - Delegates to repository methods while providing a consistent service API
 *
 * Type parameters:
 * - [E]: entity type extending [BaseEntity]
 * - [R]: repository type extending [BaseRepository] for [E]
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@Transactional(readOnly = true)
abstract class AbstractService<E, R> : BaseService<E>
        where E : BaseEntity,
              R : BaseRepository<E> {

    @Autowired(required = true)
    protected lateinit var repository: R

    override fun getById(id: Long): E =
        repository.findById(id).orElseThrow { NoSuchElementException("Entity not found: id=$id") }

    override fun findById(id: Long): E? = repository.findByIdOrNull(id)

    override fun existsById(id: Long): Boolean = repository.existsById(id)

    override fun findAll(): List<E> = repository.findAll()

    override fun findAllById(ids: Iterable<Long>): List<E> = repository.findAllById(ids)

    override fun findAll(specification: Specification<E>?): List<E> =
        if (specification == null) repository.findAll() else repository.findAll(specification)

    override fun findAll(specification: Specification<E>?, pageable: Pageable): Page<E> =
        if (specification == null) repository.findAll(pageable) else repository.findAll(specification, pageable)

    override fun count(): Long = repository.count()


    @Transactional
    override fun save(entity: E): E = repository.save(entity)

    @Transactional
    override fun saveAll(entities: Iterable<E>): List<E> = repository.saveAll(entities)


    @Transactional
    override fun deleteById(id: Long) = repository.deleteById(id)

    @Transactional
    override fun delete(entity: E) = repository.delete(entity)
}
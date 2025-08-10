package trik.testsys.sac.data.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import trik.testsys.sac.data.entity.BaseEntity

/**
 * Base Spring Data repository for all entities extending [BaseEntity].
 *
 * Extends [JpaRepository] for CRUD operations and [JpaSpecificationExecutor]
 * for flexible criteria queries. Marked as [NoRepositoryBean] so Spring
 * does not try to instantiate it directly.
 *
 * Consumers should extend this interface for their concrete entities, e.g.:
 * `interface ProjectRepository : BaseRepository<ProjectEntity>`
 *
 * @author Roman Shishkin
 * @since 1.1.0
 */
@NoRepositoryBean
interface BaseRepository<T : BaseEntity> : JpaRepository<T, Long>, JpaSpecificationExecutor<T>


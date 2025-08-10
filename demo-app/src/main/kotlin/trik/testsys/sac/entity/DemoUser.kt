package trik.testsys.sac.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import trik.testsys.sac.data.entity.AbstractEntity.Companion.TABLE_PREFIX
import trik.testsys.sac.data.entity.user.AbstractUserEntity

/**
 * Demo concrete user entity for showcasing repository usage.
 */
@Entity
@Table(name = "${TABLE_PREFIX}users")
class DemoUser(
    accessToken: String,
    name: String
) : AbstractUserEntity(accessToken, name)



package trik.testsys.sac.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import trik.testsys.sac.data.entity.BaseEntity.Companion.TABLE_PREFIX
import trik.testsys.sac.data.entity.user.UserEntity

/**
 * Demo concrete user entity for showcasing repository usage.
 */
@Entity
@Table(name = "${TABLE_PREFIX}users")
class DemoUser(
    accessToken: String,
    name: String
) : UserEntity(accessToken, name)



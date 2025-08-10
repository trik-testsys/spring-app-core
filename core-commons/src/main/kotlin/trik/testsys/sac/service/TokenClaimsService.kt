package trik.testsys.sac.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import trik.testsys.sac.data.entity.user.AbstractUserEntity
import trik.testsys.sac.data.service.user.UserService

@Service
class TokenClaimsService(
    private val userService: UserService<*>
) {

    data class UserTokenData(
        val userId: Long?,
        val name: String,
        val privileges: List<String>
    )

    @Transactional(readOnly = true)
    fun resolve(accessToken: String): UserTokenData? {
        val user = userService.findByAccessToken(accessToken) as? AbstractUserEntity ?: return null
        val privileges = user.privilegeCodes.toList()
        return UserTokenData(user.id, user.name, privileges)
    }
}



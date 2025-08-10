package trik.testsys.sac.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import trik.testsys.sac.entity.DemoUser
import trik.testsys.sac.repository.DemoUserRepository

/**
 * Service for modifying privileges of demo users.
 */
@Service
class DemoUserPrivilegeService(
    private val demoUserRepository: DemoUserRepository
) {

    /**
     * Adds the given privilege codes to the user with the specified id.
     * Returns the updated user, or throws if not found.
     */
    @Transactional
    fun addPrivilegesToUser(userId: Long, privilegeCodes: Collection<String>): DemoUser {
        val user = demoUserRepository.findById(userId).orElseThrow {
            NoSuchElementException("User not found: id=$userId")
        }

        user.privilegeCodes.addAll(privilegeCodes)
        return demoUserRepository.save(user)
    }
}



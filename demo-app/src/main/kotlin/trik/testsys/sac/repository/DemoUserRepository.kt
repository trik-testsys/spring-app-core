package trik.testsys.sac.repository

import org.springframework.stereotype.Repository
import trik.testsys.sac.data.repository.user.UserRepository
import trik.testsys.sac.entity.DemoUser

@Repository
interface DemoUserRepository : UserRepository<DemoUser>



package trik.testsys.sac.service

import org.springframework.stereotype.Service
import trik.testsys.sac.data.service.user.AbstractUserService
import trik.testsys.sac.entity.DemoUser
import trik.testsys.sac.repository.DemoUserRepository

@Service
class DemoUserService : AbstractUserService<DemoUser, DemoUserRepository>()

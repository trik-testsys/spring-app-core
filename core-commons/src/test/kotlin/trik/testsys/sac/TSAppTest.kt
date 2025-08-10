package trik.testsys.sac

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import trik.testsys.sac.application.TSApp
import trik.testsys.sac.data.entity.user.AbstractUserEntity
import trik.testsys.sac.data.service.user.UserService

/**
 * @author Roman Shishkin
 * @since 1.0.0
 */
@SpringBootTest(classes = [TSApp::class])
class TSAppTest {

    @MockBean
    private lateinit var userService: UserService<AbstractUserEntity>

    @Test
    fun contextLoads() = Unit
}
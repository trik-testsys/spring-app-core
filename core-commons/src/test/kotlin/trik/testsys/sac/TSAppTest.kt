package trik.testsys.sac

import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import trik.testsys.sac.application.TSApp
import trik.testsys.sac.data.service.user.UserService

/**
 * @author Roman Shishkin
 * @since 1.0.0
 */
@SpringBootTest(classes = [TSApp::class])
class TSAppTest {

    @MockitoBean
    private val userService = mock<UserService<*>>()

    @Test
    fun contextLoads() = Unit
}
package trik.testsys.sac

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import trik.testsys.sac.application.TSApp

/**
 * @author Roman Shishkin
 * @since 1.0.0
 */
@SpringBootTest(classes = [TSApp::class])
class TSAppTest {

    @Test
    fun contextLoads() = Unit
}
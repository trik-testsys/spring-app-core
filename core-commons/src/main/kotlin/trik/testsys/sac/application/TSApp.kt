package trik.testsys.sac.application

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author Roman Shishkin
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = ["trik.testsys.*"])
class TSApp {

    companion object {

        /**
         * @author Roman Shishkin
         * @since 1.0.0
         */
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(TSApp::class.java, *args)
        }
    }
}
package trik.testsys.sac.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.config.security.JwtSecurityConfig.Companion.API_PERMIT_ALL_PATH
import trik.testsys.sac.controller.response.ResponseData

@RestController
@RequestMapping(API_PERMIT_ALL_PATH)
class DemoController(
    @Value("\${spring.application.name}") private val appName: String
) {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<Map<String, Any?>> {
        val data = DemoResponseData(
            message = "Hello from $appName"
        )

        return ResponseEntity.ok(data.toMap())
    }

    data class DemoResponseData(
        val message: String
    ) : ResponseData {

        override val kClass = this::class
    }
}
package trik.testsys.sac.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.controller.response.ResponseData
import kotlin.reflect.KClass

@RestController
@RequestMapping("/api/demo")
class DemoController(
    @Value("\${spring.application.name}") private val appName: String
) {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<ResponseData> {
        val data = DemoResponseData(
            message = "Hello from $appName"
        )

        val a = data.toMap()

        return ResponseEntity.ok(data)
    }

    data class DemoResponseData(
        val message: String
    ) : ResponseData {

        override val kClass = this::class
    }
}
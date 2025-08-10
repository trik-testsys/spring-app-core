package trik.testsys.sac.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.config.security.JwtSecurityConfig.Companion.API_PERMIT_ALL_PATH
import trik.testsys.sac.controller.response.ResponseData
import trik.testsys.sac.entity.DemoUser
import trik.testsys.sac.repository.DemoUserRepository

@RestController
@RequestMapping(API_PERMIT_ALL_PATH)
class DemoController(
    @Value("\${spring.application.name}") private val appName: String,
    private val demoUserRepository: DemoUserRepository
) {

    @GetMapping("/hello")
    fun hello(): ResponseEntity<DemoResponseData> {
        val data = DemoResponseData(
            message = "Hello from $appName"
        )

        return ResponseEntity.ok(data)
    }

    @PostMapping("/users/{name}")
    fun createUser(@PathVariable name: String): ResponseEntity<DemoUser> {
        val user = DemoUser(accessToken = "token-$name", name = name)
        return ResponseEntity.ok(demoUserRepository.save(user))
    }

    @GetMapping("/users/token/{token}")
    fun getUserByToken(@PathVariable token: String): ResponseEntity<DemoUser?> =
        ResponseEntity.ok(demoUserRepository.findByAccessToken(token))

    data class DemoResponseData(
        val message: String
    ) : ResponseData
}
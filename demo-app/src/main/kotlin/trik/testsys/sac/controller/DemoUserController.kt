package trik.testsys.sac.controller

import jakarta.persistence.criteria.Predicate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.config.security.JwtSecurityConfig.Companion.API_PERMIT_ALL_PATH
import trik.testsys.sac.controller.response.ResponseData
import trik.testsys.sac.entity.DemoUser
import trik.testsys.sac.service.DemoUserService

@RestController
@RequestMapping("$API_PERMIT_ALL_PATH/users")
class DemoUserController(
    private val demoUserService: DemoUserService
) {


    @PostMapping("/{name}")
    fun createUser(@PathVariable name: String): ResponseEntity<DemoUsersResponseData> {
        val user = DemoUser(accessToken = "token-$name", name = name)
        val savedUser = demoUserService.save(user)

        val body = DemoUsersResponseData(listOf(savedUser))
        return ResponseEntity.ok(body)
    }

    @GetMapping("/token/{token}")
    fun getUserByToken(@PathVariable token: String): ResponseEntity<DemoUsersResponseData> {
        val user = demoUserService.findByAccessToken(token)
        val body = DemoUsersResponseData(listOf(user))

        return ResponseEntity.ok(body)
    }

    @GetMapping("/custom")
    fun getUsersByCustom(
        @RequestParam name: String?,
        @RequestParam token: String?
    ): ResponseEntity<DemoUsersResponseData> {
        val demoUsers = demoUserService.findAll { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            name?.let {
                val predicate = criteriaBuilder.equal(root.get<String>("name"), it)
                predicates += predicate
            }

            token?.let {
                val predicate = criteriaBuilder.equal(root.get<String>("accessToken"), it)
                predicates += predicate
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }

        val body = DemoUsersResponseData(demoUsers)
        return ResponseEntity.ok(body)
    }

    data class DemoUsersResponseData(
        val users: List<DemoUser?>
    ) : ResponseData
}
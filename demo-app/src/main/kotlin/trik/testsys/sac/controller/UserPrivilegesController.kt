package trik.testsys.sac.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import trik.testsys.sac.controller.response.ResponseData
import trik.testsys.sac.entity.DemoUser
import trik.testsys.sac.entity.Privilege
import trik.testsys.sac.service.DemoUserService

@RestController
@RequestMapping("/api/users")
class UserPrivilegesController(
    private val demoUserService: DemoUserService
) {

    data class PrivilegesRequest(
        val userId: Long,
        val privileges: List<Privilege>
    )

    data class DemoUsersResponseData(
        val users: List<DemoUser?>
    ) : ResponseData

    @PostMapping("/privileges/add")
    fun addPrivileges(@RequestBody request: PrivilegesRequest): ResponseEntity<DemoUsersResponseData> {
        val user = demoUserService.findById(request.userId) ?: return ResponseEntity.notFound().build()

        request.privileges.forEach { user.privilegeCodes.add(it.code) }
        val savedUser = demoUserService.save(user)
        return ResponseEntity.ok(DemoUsersResponseData(listOf(savedUser)))
    }

    @PostMapping("/privileges/remove")
    fun removePrivileges(@RequestBody request: PrivilegesRequest): ResponseEntity<DemoUsersResponseData> {
        val user = demoUserService.findById(request.userId) ?: return ResponseEntity.notFound().build()

        request.privileges.forEach { user.privilegeCodes.remove(it.code) }
        val savedUser = demoUserService.save(user)
        return ResponseEntity.ok(DemoUsersResponseData(listOf(savedUser)))
    }
}



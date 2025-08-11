package trik.testsys.sac.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import trik.testsys.sac.privilege.DemoUserPrivilege
import trik.testsys.sac.service.security.AuthorityProvider

/**
 * Demo AuthorityProvider that requires ADMIN for selected endpoints.
 */
@Service
class AdminAuthorityProvider : AuthorityProvider {

    override val patterns: Iterable<String> = listOf(
        "/api/secure/me"
    )

    override val authorities: Iterable<GrantedAuthority> = listOf(
        SimpleGrantedAuthority(DemoUserPrivilege.ADMIN.code)
    )
}



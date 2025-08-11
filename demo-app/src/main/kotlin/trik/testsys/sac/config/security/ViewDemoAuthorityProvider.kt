package trik.testsys.sac.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import trik.testsys.sac.privilege.DemoUserPrivilege
import trik.testsys.sac.service.security.AuthorityProvider

/**
 * Demo AuthorityProvider that requires VIEW_DEMO for selected endpoints.
 */
@Service
class ViewDemoAuthorityProvider : AuthorityProvider {

    override val patterns: Iterable<String> = listOf(
        "/api/secure/hello"
    )

    override val authorities: Iterable<GrantedAuthority> = listOf(
        SimpleGrantedAuthority(DemoUserPrivilege.VIEW_DEMO.code)
    )
}



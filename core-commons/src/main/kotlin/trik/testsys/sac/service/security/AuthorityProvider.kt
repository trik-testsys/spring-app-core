package trik.testsys.sac.service.security

import org.springframework.security.core.GrantedAuthority

/**
 * Interface for implementation, which will provide authority settings.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
interface AuthorityProvider {

    val patterns: Iterable<String>

    val authorities: Iterable<GrantedAuthority>
}
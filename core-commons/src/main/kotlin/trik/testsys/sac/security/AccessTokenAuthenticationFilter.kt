package trik.testsys.sac.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import trik.testsys.sac.data.service.user.UserService

/**
 * A pre-authentication filter that:
 * - Looks for a custom header `X-Access-Token` with a user access token.
 * - If present and no authentication exists yet, loads the user and builds a JWT-based Authentication.
 * - If a Bearer token is present but expired/invalid, still attempts access-token based authentication as fallback.
 *
 * This enables endpoints to be automatically authorized when clients only send `X-Access-Token` and do not
 * manage JWT lifecycles. The service will generate a short-lived JWT transparently.
 *
 * Downstream applications should provide a concrete [UserService] bean for their user entity type.
 *
 * @author Roman Shishkin
 * @since %CURRENT_VERSION%
 */
@Component
class AccessTokenAuthenticationFilter(
    private val userService: UserService<*>,
    private val jwtTokenService: JwtTokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val context = SecurityContextHolder.getContext()
            if (context.authentication == null) {
                val accessToken = resolveAccessToken(request)
                if (!accessToken.isNullOrBlank()) {
                    val user = userService.findByAccessToken(accessToken)
                    if (user != null) {
                        val auth = jwtTokenService.buildAuthenticationForUser(user)
                        attachDetails(auth, request)
                        SecurityContextHolder.getContext().authentication = auth
                        // Also attach freshly minted JWT to response for clients to store
                        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer ${auth.token.tokenValue}")
                    } else {
                        log.debug("Access token provided but user not found")
                    }
                }
            }
        } catch (ex: Exception) {
            log.warn("AccessTokenAuthenticationFilter failed: ${ex.message}")
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveAccessToken(request: HttpServletRequest): String? =
        request.getHeader(ACCESS_TOKEN_HEADER)
            ?: request.getParameter(ACCESS_TOKEN_PARAM)

    private fun attachDetails(authentication: AbstractAuthenticationToken, request: HttpServletRequest) {
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
    }

    companion object {

        private val log = LoggerFactory.getLogger(AccessTokenAuthenticationFilter::class.java)

        const val ACCESS_TOKEN_HEADER = "X-Access-Token"
        const val ACCESS_TOKEN_PARAM = "accessToken"
    }
}



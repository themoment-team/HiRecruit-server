package site.hirecruit.hr.domain.token.filter

import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.token.service.TokenService
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * JWT 인증을 진행하는 filter
 *
 * @author 정시원
 */
@Profile("token-auth")
class JwtFilter(
    private val tokenService: TokenService
): OncePerRequestFilter() {

    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val accessToken: String? = getAccessToken(request)
        if(accessToken != null && tokenService.verifyToken(accessToken)){
            val authUserInfo = this.tokenService.getAuthUserInfoByAccessTokenClaim(accessToken)
            SecurityContextHolder.getContext().authentication = createAuthentication(authUserInfo!!)
        }

        filterChain.doFilter(request, response)
    }

    private fun getAccessToken(request: HttpServletRequest): String?{
        val cookies = request.cookies.iterator()
        cookies.forEach {cookie ->
            if(cookie.name == "ACCESS_TOKEN")
                return cookie.value
        }
        return null
    }

    private fun createAuthentication(authUserInfo: AuthUserInfo): Authentication {
        return UsernamePasswordAuthenticationToken(
            authUserInfo, "",
            Collections.singleton(SimpleGrantedAuthority(authUserInfo.role.role))
        )
    }
}
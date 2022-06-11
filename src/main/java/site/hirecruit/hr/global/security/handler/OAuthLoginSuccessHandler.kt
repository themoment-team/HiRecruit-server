package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.server.Cookie.SameSite
import org.springframework.http.ResponseCookie
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import site.hirecruit.hr.domain.auth.entity.Role
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * OAuthLogin 성공 후 특정 uri로 리다이렉트 하는 핸들러
 *
 * @author 정시원
 * @since 1.0
 */
@Component
class OAuthLoginSuccessHandler(
    @Value("\${oauth2.login.success.redirect-base-uri}") val redirectBaseUri: String,
): AuthenticationSuccessHandler {
    /**
     * oauth2 login성공 후 쿠키를 SameSite None으로 설정 후 프론트엔드 웹사이트로 리다이렉트
     */
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ){
        val redirectUri = buildRedirectUri(this.redirectBaseUri, authentication)
        response.sendRedirect(redirectUri)
    }

    private fun buildRedirectUri(redirectBaseUri: String, authentication: Authentication): String{
        var redirectUri = "$redirectBaseUri/"

        val isGuest = (authentication.authorities as Collection<GrantedAuthority>)
            .contains(SimpleGrantedAuthority(Role.GUEST.role))
        if(isGuest) redirectUri += "?is_first=true"

        return redirectUri
    }
}
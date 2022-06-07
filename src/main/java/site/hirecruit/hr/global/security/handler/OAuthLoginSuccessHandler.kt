package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
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
    @Value("\${hr.domain}") val cookieDomain: String,
): AuthenticationSuccessHandler
{
    /**
     * oauth2 login성공 후 프론트엔드 웹사이트로 리다이렉트
     */
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ){
        var redirectUri = this.redirectBaseUri + "/"
        val isGuest = (authentication.authorities as Collection<SimpleGrantedAuthority>)
            .contains(SimpleGrantedAuthority(Role.GUEST.role))
        if(isGuest) //
            redirectUri += "?is-first=true"
        response.sendRedirect(redirectUri)
    }

}
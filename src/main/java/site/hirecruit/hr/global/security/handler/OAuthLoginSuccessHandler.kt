package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * OAuthLogin 성공 후 특정 uri로 리다이렉트 하는 핸들러
 * USER_TYPE쿠키를 발급한다.
 *
 * @author 정시원
 * @since 1.1.1
 */
@Component
class OAuthLoginSuccessHandler(
    @Value("\${hr.domain}") val hrDomain: String,
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
        val userTypeCookie = createUserTypeCookie(authentication)
        response.addCookie(userTypeCookie)
        response.sendRedirect(this.redirectBaseUri)
    }

    private fun createUserTypeCookie(authentication: Authentication): Cookie{
        val userType = authentication.authorities.first()
            .authority
            .removePrefix("ROLE_")
        val userTypeCookie = Cookie("USER_TYPE", userType)
        userTypeCookie.maxAge = 86400
        userTypeCookie.path = "/"
        userTypeCookie.domain = hrDomain
        return userTypeCookie
    }

}
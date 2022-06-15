package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Logout 성공 후 수행되는 Handler
 *
 * @since 1.0
 * @author 정시원
 */
@Component
class CustomLogoutSuccessHandler(
    @Value("\${oauth2.login.success.redirect-base-uri}") val redirectBaseUri: String,
    @Value("\${server.servlet.session.cookie.domain}") val domain: String
): LogoutSuccessHandler {

    /**
     * logout성공 후 잔류하고 있는 세션 관련 cookie들을 삭제한다.
     * max-age = 0 이 쿠키를 삭제한다는 의미이다.
     */
    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        response.status = HttpServletResponse.SC_OK

        val hrsessionCookie = Cookie("HRSESSION", null)
        deleteCookieBuild(hrsessionCookie)

        response.addCookie(hrsessionCookie)

        response.sendRedirect(redirectBaseUri)
    }

    private fun deleteCookieBuild(cookie: Cookie){
        cookie.secure = true
        cookie.maxAge = 0 // 쿠키 삭제하게 위해 maxAge 0으로 설정
        cookie.isHttpOnly = true
        cookie.domain = domain
        cookie.path = "/"
    }


}
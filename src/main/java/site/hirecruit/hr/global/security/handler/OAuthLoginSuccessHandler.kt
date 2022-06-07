package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.server.Cookie.SameSite
import org.springframework.http.ResponseCookie
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
@org.springframework.stereotype.Controller
@RequestMapping("/api/v1/auth/oauth2/success")
class OAuthLoginSuccessHandler(
    @Value("\${oauth2.login.success.redirect-base-uri}") val redirectBaseUri: String,
    @Value("\${hr.domain}") val cookieDomain: String
) {
    /**
     * oauth2 login성공 후 쿠키를 SameSite None으로 설정 후 프론트엔드 웹사이트로 리다이렉트
     */
    @GetMapping
    private fun loginSuccessHandler(
        authentication: Authentication,
        request: HttpServletRequest,
        response: HttpServletResponse){

        val findSessionCookie = findSessionId(request)
        val reMakeSessionCookie = ResponseCookie.from("HRSESSION", findSessionCookie.value)
            .httpOnly(true)
            .secure(true)
            .sameSite(SameSite.NONE.attributeValue())
            .domain(cookieDomain)
            .maxAge(findSessionCookie.maxAge.toLong())
            .build()
        response.addHeader("Set-Cookie", reMakeSessionCookie.toString()) // findSessionCookie를 SameSite None으로 설정

        val redirectUri = buildRedirectUri(this.redirectBaseUri, authentication, reMakeSessionCookie)
        response.sendRedirect(redirectUri)
    }

    private fun buildRedirectUri(redirectBaseUri: String, authentication: Authentication, sessionCookie: ResponseCookie): String{
        var redirectUri = "$redirectBaseUri/"

        val isGuest = (authentication.authorities as Collection<GrantedAuthority>)
            .contains(SimpleGrantedAuthority(Role.GUEST.role))
        if(isGuest)
            redirectUri += "?is-first=true"

        redirectUri += "&HRSESSION=${sessionCookie.value}"

        return redirectUri
    }

    private fun findSessionId(request: HttpServletRequest): Cookie{
        var sessionCookie: Cookie? = null
        for (cookie in request.cookies) {
            if(cookie.name == "HRSESSION"){
                sessionCookie = cookie
            }
        }
        return sessionCookie ?: throw IllegalStateException("Session could not found")
    }
}
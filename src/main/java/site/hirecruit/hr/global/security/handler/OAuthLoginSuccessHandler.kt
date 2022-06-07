package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
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
     * oauth2 login성공 후 프론트엔드 웹사이트로 리다이렉트
     */
    @GetMapping
    private fun loginSuccessHandler(@CurrentAuthUserInfo authUserInfo: AuthUserInfo, request: HttpServletRequest, response: HttpServletResponse){
        var sessionCookie: Cookie? = null
        for (cookie in request.cookies) {
            if(cookie.name == "HRSESSION"){
                sessionCookie = cookie
            }
        }
        sessionCookie ?: throw IllegalStateException("")

        response.reset()
        response.addHeader(
            "Set-Cookie",
            "HRSESSION=${sessionCookie.value}; " +
                    "HttpOnly; " +
                    "secure=true; " +
                    "SameSite=none; " +
                    "expires=${sessionCookie.maxAge}; " +
                    "domain=$cookieDomain; " +
                    "path=/;"
        )

        var redirectUri = this.redirectBaseUri + "/"


        if(authUserInfo.role == Role.GUEST) //
            redirectUri += "?is-first=true"

        redirectUri += "&HRSESSION=${sessionCookie.value}"

        response.sendRedirect(redirectUri)
    }
}
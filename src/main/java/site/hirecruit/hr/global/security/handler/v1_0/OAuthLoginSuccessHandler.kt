package site.hirecruit.hr.global.security.handler.v1_0

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.web.util.UriComponentsBuilder
import site.hirecruit.hr.domain.user.entity.Role
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * OAuthLogin 성공 후 특정 uri로 리다이렉트 하는 핸들러
 *
 * @author 정시원
 * @since 1.0
 */
@Deprecated("v1.0에 사용되었던 레거시 입니다.")
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
        val isGuest = (authentication.authorities as Collection<GrantedAuthority>)
            .contains(SimpleGrantedAuthority(Role.GUEST.role))
        val redirectUriBuild = UriComponentsBuilder
            .fromUriString(redirectBaseUri)
            .queryParam("is_first", isGuest)
            .queryParam("is_login", true) // 로그인 여부 전달
            .build()

        return redirectUriBuild.toUriString()
    }
}
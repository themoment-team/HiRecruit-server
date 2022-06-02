package site.hirecruit.hr.global.security.handler

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo

/**
 * OAuthLogin 성공 후 특정 uri로 리다이렉트 하는 핸들러
 *
 * @author 정시원
 * @since 1.0
 */
@org.springframework.stereotype.Controller
@RequestMapping("/api/v1/auth/oauth2/success")
class OAuthLoginSuccessHandler(
    @Value("\${oauth2.login.success.redirect-base-uri}") val redirectBaseUri: String
) {

    /**
     * oauth2 login성공 후 프론트엔드 웹사이트로 리다이렉트
     */
    @GetMapping
    private fun loginSuccessHandler(@CurrentAuthUserInfo authUserInfo: AuthUserInfo): String{
        if(authUserInfo.role == Role.GUEST) //
            return "redirect:${this.redirectBaseUri}?is-first=true" // 만약 소셜로그인이 처음이라면 쿼리스트링으로 알려줌
        return "redirect:${this.redirectBaseUri}"
    }
}
package site.hirecruit.hr.global.security.handler

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
class OAuthLoginSuccessHandler {

    /**
     * 프론트앤드 웹사이드의 uri를
     */
    @GetMapping
    private fun loginSuccessHandler(@CurrentAuthUserInfo authUserInfo: AuthUserInfo): String{
        if(authUserInfo.role == Role.GUEST)
            return "redirect:http://localhost:3000?is-fist=true"
        return "redirect:http://localhost:3000"
    }
}
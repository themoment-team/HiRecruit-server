package site.hirecruit.hr.domain.auth.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import site.hirecruit.hr.domain.user.service.UserRegistrationService
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.UserRegistrationDto
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import site.hirecruit.hr.global.util.CookieUtil
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userRegistrationService: UserRegistrationService,
    @Value("\${hr.domain}") private val hrDomain: String
) {

    @PostMapping("/registration")
    private fun registration(
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,

        @RequestBody  @Valid
        userRegistrationDto: UserRegistrationDto,

        response: HttpServletResponse
    ): ResponseEntity<AuthUserInfo>{
        val registeredAuthUserInfo = userRegistrationService.registration(authUserInfo, userRegistrationDto)

        response.addCookie(CookieUtil.userTypeCookie(registeredAuthUserInfo.role.name, hrDomain)) // 등록된 유저의 role을 USER_TYPE 쿠키로 넘겨줌
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(registeredAuthUserInfo)
    }

}
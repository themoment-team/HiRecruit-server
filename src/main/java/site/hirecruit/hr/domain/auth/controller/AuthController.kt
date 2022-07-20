package site.hirecruit.hr.domain.auth.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationDto
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationRequestDto
import site.hirecruit.hr.domain.user.dto.UserRegistrationDto
import site.hirecruit.hr.domain.user.service.UserRegistrationService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import site.hirecruit.hr.global.util.CookieUtil
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Deprecated("해당 controller는 추후 사용되지 않을 예정입니다. domain.user.controller.UserRegistrationController로 해당 controller의 역할이 이전될 예정입니다.")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    @Qualifier("workerUserRegistrationService")
    private val regularUserRegistrationService: UserRegistrationService<RegularUserRegistrationDto>,

    @Value("\${hr.domain}") private val hrDomain: String
) {

    @PostMapping("/registration")
    private fun registration(
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,

        @RequestBody @Valid
        userRegistrationDto: RegularUserRegistrationRequestDto,

        response: HttpServletResponse
    ): ResponseEntity<AuthUserInfo> {
        val regularUserRegistrationDto = RegularUserRegistrationDto(
            githubId = authUserInfo.githubId,
            githubLoginId = authUserInfo.githubLoginId,
            profileImgUri = authUserInfo.profileImgUri,
            userRegistrationInfo = userRegistrationDto
        )
        val registeredAuthUserInfo = regularUserRegistrationService.registration(regularUserRegistrationDto)

        response.addCookie(CookieUtil.userTypeCookie(registeredAuthUserInfo.role.name, hrDomain)) // 등록된 유저의 role을 USER_TYPE 쿠키로 넘겨줌
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(registeredAuthUserInfo)
    }

}
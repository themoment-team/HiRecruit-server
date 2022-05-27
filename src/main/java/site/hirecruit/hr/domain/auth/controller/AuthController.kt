package site.hirecruit.hr.domain.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import site.hirecruit.hr.domain.auth.service.UserRegistrationService
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserRegistrationDto
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val userRegistrationService: UserRegistrationService
) {

    @PostMapping("/registration")
    private fun registration(
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,

        @RequestBody  @Valid
        userRegistrationDto: UserRegistrationDto
    ): ResponseEntity<AuthUserInfo>{
        val registeredAuthUserInfo = userRegistrationService.registration(authUserInfo, userRegistrationDto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(registeredAuthUserInfo)
    }

}
package site.hirecruit.hr.domain.user.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.UserUpdateDto
import site.hirecruit.hr.domain.user.service.UserUpdateService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import site.hirecruit.hr.global.util.CookieUtil
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userUpdateService: UserUpdateService,

    @Value("\${hr.domain}")
    private val hrDomain: String
) {

    @PatchMapping("/me")
    fun updateUser(
        @ApiIgnore
        @CurrentAuthUserInfo
        authUserInfo: AuthUserInfo,

        @RequestBody @Valid
        updateDto: UserUpdateDto,

        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<Unit>{
        val updatedAuthUserInfo = userUpdateService.update(updateDto, authUserInfo)

        val userTypeCookie = CookieUtil.userTypeCookie(updatedAuthUserInfo.role.name, hrDomain)
        response.addCookie(userTypeCookie)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }
}
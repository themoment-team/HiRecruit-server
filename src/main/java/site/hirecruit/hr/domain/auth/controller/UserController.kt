package site.hirecruit.hr.domain.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserUpdateDto
import site.hirecruit.hr.domain.auth.service.UserUpdateService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userUpdateService: UserUpdateService
) {

    @PatchMapping("/me")
    fun updateUser(
        @ApiIgnore
        @CurrentAuthUserInfo
        authUserInfo: AuthUserInfo,

        @RequestBody @Valid
        updateDto: UserUpdateDto
    ): ResponseEntity<Unit>{
        userUpdateService.update(updateDto, authUserInfo)

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }
}
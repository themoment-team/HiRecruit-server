package site.hirecruit.hr.domain.mentor.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.mentor.dto.MentorDto
import site.hirecruit.hr.domain.mentor.service.MentorService
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import site.hirecruit.hr.global.util.CookieMakerUtil
import springfox.documentation.annotations.ApiIgnore
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/mentor")
class MentorController(
    private val mentorServiceImpl: MentorService,
    @Value("\${server.servlet.session.cookie.domain}") private val hrDomain: String
) {

    /**
     * role: worker가 mentor가 되기 위해서 거쳐야 하는 인증 API 입니다.
     *
     * @param workerId 등업대상
     * @param authUserInfo AOP, 현재 로그인 된 사용자
     * @return ResponseEntity
     */
    @PostMapping("/promotion/process/{workerId}")
    fun executeMentorPromotion(
        @PathVariable
        workerId: Long,
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,
    ): ResponseEntity<Map<String, String>> {

        // service 실행
        mentorServiceImpl.mentorPromotionProcess(workerId, authUserInfo)

        // ok response
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                mapOf("message" to "workerId: $workerId 에게 성공적으로 인증번호를 보냈습니다.")
            )
    }

    /**
     * 인증 체계에 대한 검증을 요청하는 controller
     *
     * @param mentorVerifyVerificationMethodRequestDto mentor 로 등업하기 위해 검증 필요한 data들
     */
    @PatchMapping("/promotion/process/verify")
    fun verifyVerificationMethod(
        @RequestBody mentorVerifyVerificationMethodRequestDto: MentorDto.MentorVerifyVerificationMethodRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {

        // 적절한 검증을 통해 mentor로 등업
        val mentorId = mentorServiceImpl.grantMentorRole(
            mentorVerifyVerificationMethodRequestDto.workerId,
            mentorVerifyVerificationMethodRequestDto.verificationCode,
        )

        val userTypeCookie = CookieMakerUtil.userTypeCookie(Role.MENTOR.name, hrDomain)
        response.addCookie(userTypeCookie)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                mapOf("message" to "workerId: $mentorId 에게 성공적으로 mentor 권한을 부여 함.")
            )
    }
}
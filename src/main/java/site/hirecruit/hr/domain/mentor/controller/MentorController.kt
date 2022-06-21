package site.hirecruit.hr.domain.mentor.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
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
     * @param authUserInfo 현재 로그인 된 사용자
     * @return ResponseEntity
     */
    @PostMapping("/promotion/process")
    fun executeMentorPromotion(
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,
    ): ResponseEntity<Map<String, String>> {

        // service 실행
        val mentorPromotionProcess =
            mentorServiceImpl.mentorPromotionProcess(authUserInfo.githubId)

        // ok response
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                mapOf("message" to "workerId: ${mentorPromotionProcess.keys.first()} 에게 성공적으로 인증번호를 보냈습니다.")
            )
    }

    /**
     * 인증 체계에 대한 검증을 요청하는 controller
     *
     * @param authUserInfo 로그인된 사용자
     * @param verificationCode 사용자가 입력한 인증번호
     */
    @PatchMapping("/promotion/process/verify")
    fun verifyVerificationMethod(
        @CurrentAuthUserInfo @ApiIgnore
        authUserInfo: AuthUserInfo,

        @RequestBody verificationCode : String,

        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {

        // 적절한 검증을 통해 mentor로 등업
        val mentorId = mentorServiceImpl.grantMentorRole(
            githubId = authUserInfo.githubId,
            verificationCode = verificationCode
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
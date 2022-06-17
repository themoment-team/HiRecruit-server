package site.hirecruit.hr.domain.mailer.verifyEmail.component

import org.springframework.stereotype.Component
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto

/**
 * 인증번호 이메일 템플릿이라는 단일 책임을 가지고 있는 클래스 입니다.
 *
 * @author 전지환
 * @since 1.2.0
 */
@Component
class VerificationCodeEmailTemplate {

    /**
     * HR 공식 인증번호 이메일 요청을 만들어주는 함수입니다.
     * 모든 파라미터는 NOT-NULL 이메일을 보내기 위해 무조건적으로 만족해야하는 최소 단위입니다.
     *
     * @param workerEmail not-null
     * @param verificationCode not-null
     * @param workerName not-null
     */
    fun createMentorEmailVerificationEmailRequest(
        workerName: String,
        verificationCode: String,
        workerEmail: String
    ): SesRequestDto.TemplateSesRequestDto {

        return SesRequestDto.TemplateSesRequestDto(
            "HiRecruitEmailAuthenticationTemplate",
            "{ \"name\":\"${workerName}\", \"authenticationCode\":\"${verificationCode}\" }",
            SesRequestDto.DestinationDto(emptyList(), emptyList(), listOf(workerEmail))
        )
    }
}
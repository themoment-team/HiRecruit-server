package site.hirecruit.hr.domain.emailTemplate.verifyEmail.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.global.util.randomNumberGenerator
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.HrSesService

@Service
class VerifyEmailServiceImpl(
    private val hrSesServiceImpl: HrSesService
) : VerifyEmailService{

    /**
     * 6자리의 인증번호를 email로 보내는 함수이다.
     */
    override fun sendVerificationCode(workerId: Long, workerName: String, targetEmail: String): String? {
        // 난수 생성
        val randomNumber = randomNumberGenerator(6)

        // 난수 저장 with workerId


        //
        return null
    }

    override fun sendEmailVerificationSES(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto): String {
        hrSesServiceImpl.sendEmailWithEmailTemplate(templateSesRequestDto)
    }


}
package site.hirecruit.hr.domain.emailTemplate.verifyEmail

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.HrSesService

@Service
class VerificationEmailSenderService(private val hrSesServiceImpl: HrSesService){

    fun sendEmailVerificationSES(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto): List<String> {
        return hrSesServiceImpl.sendEmailWithEmailTemplate(templateSesRequestDto)
    }
}
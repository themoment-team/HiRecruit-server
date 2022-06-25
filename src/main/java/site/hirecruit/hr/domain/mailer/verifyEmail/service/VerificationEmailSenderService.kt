package site.hirecruit.hr.domain.mailer.verifyEmail.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.HrSesService

/**
 * 인증번호 발송에 대한 단일 책임만 있는 서비스 입니다.
 *
 * @author 전지환
 * @since 1.2.0
 */
@Service
class VerificationEmailSenderService(private val hrSesServiceImpl: HrSesService){

    /**
     * HR support / 인증번호 발송 에만 책임이 있는 서비스 입니다.
     * see 클래스의 메소드를 Wrapping 하여 OCP 하게 구현하였습니다.
     *
     * @see HrSesService.sendEmailWithEmailTemplate
     */
    suspend fun sendEmailVerificationSES(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto): List<String> {
        hrSesServiceImpl.sendEmailWithEmailTemplate(templateSesRequestDto)
        return templateSesRequestDto.destinationDto.toAddress
    }
}
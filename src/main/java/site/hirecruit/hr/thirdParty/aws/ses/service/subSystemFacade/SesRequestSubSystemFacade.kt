package site.hirecruit.hr.thirdParty.aws.ses.service.subSystemFacade

import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest

/**
 * SES Sdk API 와 통신하여 client가 굳이 알 필요 subSystem을 Facade화 했습니다.
 *
 * @author 전지환
 * @since 1.2.0
 */
interface SesRequestSubSystemFacade {
    fun createHiRecruitSupportEmailRequest(templateRequestDto: SesRequestDto.TemplateSesRequestDto): SendEmailRequest
}
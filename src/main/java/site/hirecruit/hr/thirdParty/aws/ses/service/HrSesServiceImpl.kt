package site.hirecruit.hr.thirdParty.aws.ses.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail.SesTemplateEmailComponent

/**
 * 개발자가 aws SES 서비스를 사용하고 싶을 때 의존해야하는 Service
 *
 * @author 전지환
 * @since 1.2.0
 */
@Service
class SesFactoryServiceImpl(
    val sesTemplateEmailComponentImpl: SesTemplateEmailComponent
): SesFactoryService {

    /**
     * 에메일 단건을 단일 사용자에게 보내는 서비스
     */
    override fun sendEmailWithEmailTemplate(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto) {
        sesTemplateEmailComponentImpl.createHiRecruitSupportEmailRequest(templateSesRequestDto)
    }
}
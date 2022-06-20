package site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail

import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest

/**
 * SES 서비스의 TemplateEmail을 사용하고자 할 때 쓸 수 있는 컴퍼넌트들의 집합입니다.
 *
 * @author 전지환
 * @since 1.2.0
 */
interface SesTemplateEmailComponent {
    fun createTemplateEmailRequest(templateRequestDto: SesRequestDto.TemplateSesRequestDto): SendEmailRequest
}
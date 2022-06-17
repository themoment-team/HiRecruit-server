package site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail

import org.springframework.stereotype.Component
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import software.amazon.awssdk.services.sesv2.model.Destination
import software.amazon.awssdk.services.sesv2.model.EmailContent
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest
import software.amazon.awssdk.services.sesv2.model.Template

private const val FROM_EMAIL_ADDRESS = "support@hirecruit.site"

@Component
class SesTemplateEmailComponentImpl : SesTemplateEmailComponent {

    /**
     * HiRecruit support-side 이메일을 요청을 보낼 때 사용하는 emailRequest
     *
     * @param templateRequestDto support email은 template을 사용한다.
     * @since 1.2.0
     * @author 전지환
     */
    override fun createTemplateEmailRequest(templateRequestDto: SesRequestDto.TemplateSesRequestDto): SendEmailRequest {

        return SendEmailRequest.builder()
            .content(
                EmailContent.builder()
                    .template(
                        Template.builder()
                            .templateName(templateRequestDto.templateName)
                            .templateData(templateRequestDto.templateData)
                            .build()
                    ).build()
            ).destination(
                Destination.builder()
                    .bccAddresses(templateRequestDto.destinationDto.bccAddress)
                    .ccAddresses(templateRequestDto.destinationDto.ccAddress)
                    .toAddresses(templateRequestDto.destinationDto.toAddress) // sandbox mode:: only verified email
                    .build()
            ).fromEmailAddress(FROM_EMAIL_ADDRESS)
            .build()
    }

}
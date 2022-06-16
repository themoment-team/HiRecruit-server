package site.hirecruit.hr.thirdParty.aws.ses.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail.SesTemplateEmailComponent
import software.amazon.awssdk.services.sesv2.SesV2Client

/**
 * 개발자가 aws SES 서비스를 사용하고 싶을 때 의존해야하는 Service
 *
 * @author 전지환
 * @since 1.2.0
 */
@Service
class HrSesServiceImpl(
    val sesTemplateEmailComponentImpl: SesTemplateEmailComponent,
    val sesCredentialService: CredentialService
): HrSesService {

    /**
     * 템플릿 이메일을 보내는 서비스
     *
     * @see `https://docs.aws.amazon.com/ko_kr/ses/latest/dg/send-personalized-email-api.html`
     * @return Boolean - 성공 여부
     */
    override fun sendEmailWithEmailTemplate(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto): List<String> {
        // templateRequest 만들기
        val templateEmailRequest = sesTemplateEmailComponentImpl.createTemplateEmailRequest(templateSesRequestDto)

        // sdk 실제 요청
        val sesClient = sesCredentialService.getSdkClient() as SesV2Client
        if (!sesClient.sendEmail(templateEmailRequest).sdkHttpResponse().isSuccessful) {
            throw Exception("templateEmail을 보낼 수 없음")
        }

        return templateSesRequestDto.destinationDto.toAddress
    }
}
package site.hirecruit.hr.thirdParty.aws.ses.service

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail.SesTemplateEmailComponent
import software.amazon.awssdk.services.sesv2.SesV2Client

private val log = KotlinLogging.logger {}

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
     * @return List<String> - 받는 사람 이메일
     */
    @Async
    override fun sendEmailWithEmailTemplate(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto) {
        log.info { "===== sendEmailWithEmailTemplate async logic ======" }

        // templateRequest 만들기
        val templateEmailRequest = sesTemplateEmailComponentImpl.createTemplateEmailRequest(templateSesRequestDto)

        // sdk 실제 요청
        val sesV2Client = sesCredentialService.getSdkClient() as SesV2Client
        if (!sesV2Client.sendEmail(templateEmailRequest).sdkHttpResponse().isSuccessful){
            throw Exception("sesV2Client가 templateEmail을 보낼 수 없음.")
        }
    }
}
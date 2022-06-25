package site.hirecruit.hr.thirdParty.aws.ses.service

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
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
    override fun sendEmailWithEmailTemplate(templateSesRequestDto: SesRequestDto.TemplateSesRequestDto): Unit = runBlocking{
        // sdk에 요청할 templateRequest 객체 만들기
        val makeTemplateRequest = async {
            log.info { "========= async makeTemplateRequest ==========" }
            sesTemplateEmailComponentImpl.createTemplateEmailRequest(templateSesRequestDto)
        }
        // SES sdk aync
        val setSdkClient = async {
            log.info { "========= async sdkSetter ==========" }
            sesCredentialService.getSdkClient() as SesV2Client
        }

        kotlin.runCatching {
            setSdkClient.await()
                .sendEmail(makeTemplateRequest.await())
        }.onFailure {
            throw Exception("sesClient가 templateEmail을 보낼 수 있는 상태가 아님")
        }
    }
}
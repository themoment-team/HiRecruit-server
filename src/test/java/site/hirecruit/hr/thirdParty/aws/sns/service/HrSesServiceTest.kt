package site.hirecruit.hr.thirdParty.aws.sns.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.service.SesCredentialService
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.HrSesServiceImpl
import site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail.SesTemplateEmailComponentImpl
import software.amazon.awssdk.services.sesv2.SesV2Client
import software.amazon.awssdk.services.sesv2.model.*


class HrSesServiceTest {

    @Test @Disabled
    @DisplayName("실제로 ses sdk 랑 상호작용하여 결과를 만들어내는 테스트:: disabled")
    fun createSendEmailRequestAndSend(
        @Autowired credentialService: SesCredentialService
    ){
        // Given
        val emailRequest = SendEmailRequest.builder()
            .content(
                EmailContent.builder()
                    .simple(
                        Message.builder()
                            .subject(Content.builder()
                                .data("HiRecruit | 하이리쿠르트 테스트 이메일 입니다.")
                                .charset("UTF-8")
                                .build())
                            .body(Body.builder()
                                .text(
                                    Content.builder()
                                        .data("테스트 이메일 입니다")
                                        .charset("UTF-8").build()
                                ).build()
                            ).build()
                    ).build()
            ).destination(Destination.builder()
                .toAddresses("jihwan.official@gmail.com") // sandbox mode:: only verified email
                .build()
            ).fromEmailAddress("support@hirecruit.site")
            .build()

        // When
        val sendEmail = credentialService.getSdkClient().sendEmail(emailRequest)

        // Then
        assertThat(sendEmail.sdkHttpResponse().isSuccessful).isTrue
    }

    @Test
    @DisplayName("단건의 templateEmail을 destination에게 정상적으로 보낼 수 있다.")
    fun templateEmailWasSendSuccessfulToDestination(){

        // Given
        val sesV2Client: SesV2Client = mockk()
        val sendEmailRequest: SendEmailRequest = mockk()
        val sesCredentialService: CredentialService = mockk()
        val sesTemplateEmailComponentImpl: SesTemplateEmailComponentImpl = mockk()
        val templateSesRequestDto: SesRequestDto.TemplateSesRequestDto = mockk()

        // mocking
        every { sesTemplateEmailComponentImpl.createTemplateEmailRequest(templateSesRequestDto) }.returns(sendEmailRequest)
        every { sesCredentialService.getSdkClient() }.returns(sesV2Client)
        every { sesV2Client.sendEmail(sendEmailRequest).sdkHttpResponse().isSuccessful }.returns(any())

        // when:: emailTemplate을 이용한 이메일을 보낸다.
        val hrSesServiceImpl = HrSesServiceImpl(sesTemplateEmailComponentImpl, sesCredentialService)
        hrSesServiceImpl.sendEmailWithEmailTemplate(templateSesRequestDto)

        // then
        verify(exactly = 1) {sesTemplateEmailComponentImpl.createTemplateEmailRequest(any()) }
        verify(exactly = 1) { sesCredentialService.getSdkClient() }
        verify(exactly = 1) { sesV2Client.sendEmail(sendEmailRequest) }
    }
}
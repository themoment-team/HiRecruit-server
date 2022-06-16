package site.hirecruit.hr.thirdParty.aws.sns.service

import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.thirdParty.aws.service.SesCredentialService
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import site.hirecruit.hr.thirdParty.aws.ses.service.HrSesServiceImpl
import site.hirecruit.hr.thirdParty.aws.ses.service.component.templateEmail.SesTemplateEmailComponent
import software.amazon.awssdk.services.sesv2.model.*

@LocalTest
@SpringBootTest
class SesServiceTest {

    @Test
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
    fun aSesMessageSendSuccessful(
        @Autowired sesFactoryService: HrSesServiceImpl,
        @Autowired sesTemplateEmailComponent: SesTemplateEmailComponent,
    ){

        val templateName = "HiRecruitEmailAuthenticationTemplate"
        val templateData = "{ \"name\":\"이선우\", \"authenticationCode\":\"1234\" }"
        val to = "hirecruit@gsm.hs.kr"

        // Given
        val templateSesRequestDto = SesRequestDto.TemplateSesRequestDto(
            templateName, templateData, SesRequestDto.DestinationDto(listOf(), listOf(), listOf(to))
        )

        // when
        sesFactoryService.sendEmailWithEmailTemplate(templateSesRequestDto)

        // then: 1. hr support 전용 SES Request 를 만든다.
//        verify(exactly=1) {sesTemplateEmailComponent.createTemplateEmailRequest(templateSesRequestDto) }
    }
}
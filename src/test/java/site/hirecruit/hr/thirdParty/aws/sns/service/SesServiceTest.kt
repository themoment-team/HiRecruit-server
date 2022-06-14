package site.hirecruit.hr.thirdParty.aws.sns.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.thirdParty.aws.service.SesCredentialService
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

}
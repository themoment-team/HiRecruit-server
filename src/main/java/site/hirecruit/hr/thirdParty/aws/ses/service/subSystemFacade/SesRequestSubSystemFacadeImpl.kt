package site.hirecruit.hr.thirdParty.aws.ses.service.facade

import org.springframework.beans.factory.annotation.Autowired
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto
import software.amazon.awssdk.services.sesv2.model.*

class SesRequestSubSystemFacadeImpl(
    @Autowired sesCredentialService: CredentialService
) : SesRequestSubSystemFacade{

    /**
     * HiRecruit support-side 이메일을 요청을 보낼 때 사용하는 subSystem
     *
     * @since 1.2.0
     * @author 전지환
     */
    override fun createEmailRequest(sesRequestDto: SesRequestDto): SendEmailRequest {
        SendEmailRequest.builder()
            .content(
                EmailContent.builder()
                    .simple(
                        Message.builder()
                            .subject(
                                Content.builder()
                                .data("HiRecruit | 하이리쿠르트 테스트 이메일 입니다.")
                                .charset("UTF-8")
                                .build())
                            .body(
                                Body.builder()
                                .text(
                                    Content.builder()
                                        .data("테스트 이메일 입니다")
                                        .charset("UTF-8").build()
                                ).build()
                            ).build()
                    ).build()
            ).destination(
                Destination.builder()
                .toAddresses("jihwan.official@gmail.com") // sandbox mode:: only verified email
                .build()
            ).fromEmailAddress("support@hirecruit.site")
            .build()
    }

}
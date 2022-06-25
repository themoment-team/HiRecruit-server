package site.hirecruit.hr.thirdParty.aws.ses.client

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import software.amazon.awssdk.services.sesv2.SesV2Client
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest

private val log = KotlinLogging.logger {}

open class HrSesClient(
    private val sesV2Client: SesV2Client
) {

    @Async
    open fun sendEmailToHrAsyncSesV2Client(sendEmailRequest: SendEmailRequest){
        log.info { "======= async sesV2Client 진입점 ========" }
        val sendEmail = sesV2Client.sendEmail(sendEmailRequest)

        for(i in 1..10 ){
            log.info { "======= $i ========" }
        }

        if (!sendEmail.sdkHttpResponse().isSuccessful){
            throw Exception("sesV2Client가 emailRequest를 처리할 수 없습니다.")
        }
    }
}
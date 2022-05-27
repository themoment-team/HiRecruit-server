package site.hirecruit.hr.thirdParty.aws.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import javax.annotation.PostConstruct


private val log = KotlinLogging.logger {}

/**
 * aws ses service 를 쓰기위한 설정 정보들이 init 됩니다.
 *
 * @author 전지환
 * @since 1.0.0
 */
@Configuration
@PropertySource("classpath:/aws/aws-ses-config.properties")
class AwsSesConfig(
    @Value("\${aws.accessKey}")
    val awsAccessKey: String,

    @Value("\${aws.secretKey}")
    val awsSecretKey: String,

    @Value("\${aws.region}")
    val awsRegion: String
){

    @PostConstruct
    fun init() {
        log.debug { "AwsSnsConfig(" +
                "awsAccessKey='$awsAccessKey', " +
                "awsSecretKey='$awsSecretKey', " +
                "awsRegion='$awsRegion'" +
                ")" }
    }
}
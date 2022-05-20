package site.hirecruit.hr.thirdParty.aws.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import site.hirecruit.hr.domain.auth.aop.log
import javax.annotation.PostConstruct

/**
 * 인스턴스 생성 시점에 aws env 설정이 대입되어 초기화 된다.
 *
 * @since 1.0.0
 * @author 전지환
 */
@Configuration
@PropertySource("classpath:/aws/aws-sns-config.properties")
class AwsSnsConfig(

    @Value("\${sns.topic.arn}")
    val snsTopicARN: String,

    @Value("\${aws.accessKey}")
    val awsAccessKey: String,

    @Value("\${aws.secretKey}")
    val awsSecretKey: String,

    @Value("\${aws.region}")
    val awsRegion: String

) {

    @PostConstruct
    fun init() {
        log.debug { "AwsSnsConfig(" +
                "snsTopicARN='$snsTopicARN', " +
                "awsAccessKey='$awsAccessKey', " +
                "awsSecretKey='$awsSecretKey', " +
                "awsRegion='$awsRegion'" +
                ")" }
    }
}
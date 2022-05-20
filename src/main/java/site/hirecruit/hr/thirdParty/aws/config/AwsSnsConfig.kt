package site.hirecruit.hr.thirdParty.aws.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import site.hirecruit.hr.domain.auth.aop.log
import javax.annotation.PostConstruct

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
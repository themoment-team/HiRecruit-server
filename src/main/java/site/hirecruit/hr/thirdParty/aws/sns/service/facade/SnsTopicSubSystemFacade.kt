package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.CreateTopicResponse

@Service
class SnsTopicSubSystemFacade {

    /**
     * aws sns 의 topic 을 생성해주는 method
     * https://ap-northeast-2.console.aws.amazon.com/sns/v3/home?region=ap-northeast-2#/homepage
     *
     * @since 1.0.0
     */
    fun createTopicRequest(topicName: String): CreateTopicRequest {

        return CreateTopicRequest.builder()
            .name(topicName)
            .build()
    }

    /**
     * sdkHttpResponse가 !isOk 대해 Exception을 발생시켜주는 HealthChecker
     *
     * @since 1.0.0
     */
    fun sdkHealthChecker(createTopic: CreateTopicResponse) {
        // sdkHttpResponse checker
        val sdkHttpResponse = createTopic.sdkHttpResponse()

        if (!sdkHttpResponse.isSuccessful) {
            throw Exception("sdkHttpResponseException ====== code: ${sdkHttpResponse.statusCode()} message: ${sdkHttpResponse.statusText()}")
        }
    }
}
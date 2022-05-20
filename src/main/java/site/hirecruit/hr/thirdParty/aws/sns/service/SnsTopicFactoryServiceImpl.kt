package site.hirecruit.hr.thirdParty.aws.sns.service

import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.CreateTopicResponse

/**
 * sns topic을 생성해주는 서비스
 *
 * @author 전지환
 * @since 1.0.0
 */
@Service
class SnsTopicFactoryServiceImpl : SnsTopicFactoryService{

    /**
     * aws sns topic을 생성해주는 서비스
     *
     * @see CreateTopicRequest.name - Constraints
     */
    override fun createTopic(topicName: String) {

        // topicRequest 생성
        val topicRequest = createTopicRequest(topicName)

        // topic 생성
        val createTopic = SnsClient.create().createTopic(topicRequest)

        // topic이 성공적으로 생성됐는지 assertion
        sdkHealthChecker(createTopic)
    }

    /**
     * aws sns 의 topic 을 생성해주는 method
     * https://ap-northeast-2.console.aws.amazon.com/sns/v3/home?region=ap-northeast-2#/homepage
     *
     * @since 1.0.0
     */
    private fun createTopicRequest(topicName: String): CreateTopicRequest? {

        return CreateTopicRequest.builder()
            .name(topicName)
            .build()
    }

    /**
     * sdkHttpResponse가 !isOk 대해 Exception을 발생시켜주는 HealthChecker
     *
     * @since 1.0.0
     */
    private fun sdkHealthChecker(createTopic: CreateTopicResponse) {
        // sdkHttpResponse checker
        val sdkHttpResponse = createTopic.sdkHttpResponse()

        if (!sdkHttpResponse.isSuccessful) {
            throw Exception("sdkHttpResponseException ====== code: ${sdkHttpResponse.statusCode()} message: ${sdkHttpResponse.statusText()}")
        }
    }
}
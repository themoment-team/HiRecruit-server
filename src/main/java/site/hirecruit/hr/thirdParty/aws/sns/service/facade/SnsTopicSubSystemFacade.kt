package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest

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
     * snsClient, aws api가 직접적으로 개입하는 로직
     *
     * @param topicRequest
     * @param snsClient sns 서비스를 사용할 자격이 있는 client
     * @throws Exception request가 정상적으로 처리되지 않았을 때.
     */
    fun servingTopicRequestToSnsClient(topicRequest: CreateTopicRequest, snsClient: SnsClient) : Boolean {

        // topic 생성
        val createTopicResponse = snsClient.createTopic(topicRequest)

        // sdkHttpResponse가 !isOk 대해 Exception을 발생시켜주는 HealthChecker
        val sdkHttpResponse = createTopicResponse.sdkHttpResponse()
        if (!sdkHttpResponse.isSuccessful){
            throw Exception("sdkHttpResponse가 기대값 isSuccessful를 만족시키지 못 함 ======== code: ${sdkHttpResponse.statusCode()}  msg: ${sdkHttpResponse.statusText()}")
        }

        return true
    }

}
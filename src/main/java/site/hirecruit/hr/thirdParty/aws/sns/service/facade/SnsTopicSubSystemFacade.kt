package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import org.springframework.stereotype.Service
import software.amazon.awssdk.http.SdkHttpResponse
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.CreateTopicResponse
import software.amazon.awssdk.services.sns.model.ListTopicsRequest
import software.amazon.awssdk.services.sns.model.ListTopicsResponse

/**
 * service layer -> facade layer -> sdk
 *
 * @since 1.0.0
 * @author 전지환
 */
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
     * listTopicRequest를 생성해주는 로직
     *
     * @return ListTopicsRequest
     */
    fun createListTopicRequest() : ListTopicsRequest{
        return ListTopicsRequest.builder().build()
    }

    /**
     * snsClient, aws api가 직접적으로 개입하는 로직
     *
     * @param topicRequest
     * @param snsClient sns 서비스를 사용할 자격이 있는 client
     * @throws Exception request가 정상적으로 처리되지 않았을 때.
     * @return createTopicResponse topic request 결과
     */
    fun servingTopicRequestToSnsClient(topicRequest: CreateTopicRequest, snsClient: SnsClient) : CreateTopicResponse? {

        // topic 생성
        val createTopicResponse = snsClient.createTopic(topicRequest)

        // topic이 정상적으로 생성 됐는지 check
        isSdkHttpResponseIsSuccessful(createTopicResponse.sdkHttpResponse())

        return createTopicResponse
    }

    /**
     * 모든 sns topic 들을 가져오는 로직
     *
     * @param listTopicRequest
     * @return listTopics
     */
    fun getAllTopicsAsList(listTopicRequest: ListTopicsRequest, snsClient: SnsClient): ListTopicsResponse? {
        val listTopics = snsClient.listTopics(listTopicRequest)
        isSdkHttpResponseIsSuccessful(listTopics.sdkHttpResponse())

        return listTopics
    }

    /**
     * sdkHttpResponse가 !isOk 대해 Exception을 발생시켜주는 HealthChecker
     *
     * @param sdkHttpResponse snsClient 결과 sdkHttpResponse
     * @throws Exception sdkHttpResponse.isSuccessful 이 아닐 때.
     */
    private fun isSdkHttpResponseIsSuccessful(sdkHttpResponse: SdkHttpResponse) {

        if (!sdkHttpResponse.isSuccessful) {
            throw Exception("sdkHttpResponse가 기대값 isSuccessful를 만족시키지 못 함 ======== code: ${sdkHttpResponse.statusCode()}  msg: ${sdkHttpResponse.statusText()}")
        }
    }
}
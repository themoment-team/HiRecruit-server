package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import mu.KotlinLogging
import org.springframework.stereotype.Service
import software.amazon.awssdk.http.SdkHttpResponse
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.*


private val log = KotlinLogging.logger {}

/**
 * service layer -> facade layer -> sdk
 *
 * @since 1.0.0
 * @author 전지환
 */
@Service
class SnsClientSubSystemFacade {

    /**
     * snsClient, aws api가 직접적으로 개입하는 로직
     *
     * @param topicRequest nullable
     * @param snsClient sns 서비스를 사용할 자격이 있는 client
     * @throws Exception request가 정상적으로 처리되지 않았을 때.
     * @return createTopicResponse topic request 결과
     */
    fun createTopic(topicRequest: CreateTopicRequest, snsClient: SnsClient) : CreateTopicResponse? {

        // topic 생성
        val createTopicResponse = snsClient.createTopic(topicRequest)

        // topic이 정상적으로 생성 됐는지 check
        isSdkHttpResponseIsSuccessful(createTopicResponse.sdkHttpResponse())

        return createTopicResponse
    }

    /**
     * 모든 sns topic 들을 가져오는 로직
     *
     * @param listTopicRequest nullable
     * @return listTopics
     */
    fun getAllTopicsAsList(listTopicRequest: ListTopicsRequest, snsClient: SnsClient): ListTopicsResponse? {
        val listTopics = snsClient.listTopics(listTopicRequest)
        isSdkHttpResponseIsSuccessful(listTopics.sdkHttpResponse())

        return listTopics
    }

    /**
     * snsClient가 개입하여 실제 topic에 email을 sub 하는 로직
     *
     * @param subscribeRequest createSubscribeRequest() 결과
     * @param snsClient 인증된 sns 클라이언트
     * @return subscriptionArn - 구독을 식별할 수 있는 subscriptionArn
     */
    fun subscribeEmail(
        subscribeRequest: SubscribeRequest,
        snsClient: SnsClient
    ) : String{

        val subscribeResponse = snsClient.subscribe(subscribeRequest)
        isSdkHttpResponseIsSuccessful(subscribeResponse.sdkHttpResponse())

        log.info {
            "Subscription ARN: ${subscribeResponse.subscriptionArn()} " +
                    "===== status: ${subscribeResponse.sdkHttpResponse().statusCode()}"
        }

        return subscribeResponse.subscriptionArn()
    }

    /**
     * sns 주제(topic)에 대한 구독(sub)이 메시지를 수신하기를 원하는지 확인하는 로직
     *
     * @param confirmSubscriptionRequest topicArn, subArn 등의 정보를 담은 confirmRequest 객체
     * @param snsClient sns 서비스의 권한이 있는 client
     * @return subscriptionArn - confirmSubArn
     */
    fun isAlreadyConfirm(confirmSubscriptionRequest: ConfirmSubscriptionRequest, snsClient: SnsClient) : String {

        // sdk에게 confirmSub 인지 확인하는 로직
        val confirmSubscription = snsClient.confirmSubscription(confirmSubscriptionRequest)
        isSdkHttpResponseIsSuccessful(confirmSubscription.sdkHttpResponse())

        return confirmSubscription.subscriptionArn()
            ?: throw Exception("topicArn: ${confirmSubscriptionRequest.topicArn()} 에 confirmSub한 Arn: ${confirmSubscriptionRequest.token()} 을 찾을 수 없음")
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
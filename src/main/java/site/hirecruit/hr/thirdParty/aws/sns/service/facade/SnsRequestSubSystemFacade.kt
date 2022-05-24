package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.sns.ProtocolType
import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionRequest
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.ListTopicsRequest
import software.amazon.awssdk.services.sns.model.SubscribeRequest

@Service
class SnsRequestSubSystemFacade {

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
    fun createListTopicRequest() : ListTopicsRequest {
        return ListTopicsRequest.builder().build()
    }


    /**
     * ConfirmSubscriptionRequest를 생성해주는 로직
     *
     * @return ConfirmSubscriptionRequest
     */
    fun createConfirmSubscriptionRequest(subscriptionToken: String, topicArn: String) : ConfirmSubscriptionRequest {

        return ConfirmSubscriptionRequest.builder()
            .token(subscriptionToken)
            .topicArn(topicArn)
            .build()
    }

    /**
     * SubscribeRequest를 만들어주는 로직
     *
     * @param targetEmail 등록하고자 하는 email
     * @param targetTopicArn email 등록 대상 topicArn
     */
    fun createSubscribeRequest(targetEmail: String, targetTopicArn: String) : SubscribeRequest {

        return SubscribeRequest.builder()
            .protocol(ProtocolType.EMAIL.toString().lowercase()) // "email" 이라는 sdk prefix 를 사용해야 함.
            .endpoint(targetEmail)
            .returnSubscriptionArn(true)
            .topicArn(targetTopicArn)
            .build()
    }

}
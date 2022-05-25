package site.hirecruit.hr.thirdParty.aws.sns.service.facade.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.sns.ProtocolType
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsRequestSubSystemFacade
import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionRequest
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.ListTopicsRequest
import software.amazon.awssdk.services.sns.model.SubscribeRequest

/**
 * snsClient에게 전달할 snsRequest 생성해주는 facade 집합
 *
 * @see software.amazon.awssdk.services.sns.model.SnsRequest
 * @since 1.0.0
 * @author 전지환
 */
@Service
class SnsRequestSubSystemFacadeImpl : SnsRequestSubSystemFacade{

    /**
     * aws sns 의 topic 을 생성해주는 method
     * https://ap-northeast-2.console.aws.amazon.com/sns/v3/home?region=ap-northeast-2#/homepage
     *
     * @since 1.0.0
     */
    override fun createTopicRequest(topicName: String): CreateTopicRequest {

        return CreateTopicRequest.builder()
            .name(topicName)
            .build()
    }


    /**
     * listTopicRequest를 생성해주는 로직
     *
     * @return ListTopicsRequest
     */
    override fun createListTopicRequest() : ListTopicsRequest {
        return ListTopicsRequest.builder().build()
    }


    /**
     * ConfirmSubscriptionRequest를 생성해주는 로직
     *
     * @return ConfirmSubscriptionRequest
     */
    override fun createConfirmSubscriptionRequest(subscriptionToken: String, topicArn: String) : ConfirmSubscriptionRequest {

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
    override fun createSubscribeRequest(targetEmail: String, targetTopicArn: String) : SubscribeRequest {

        return SubscribeRequest.builder()
            .protocol(ProtocolType.EMAIL.toString().lowercase()) // "email" 이라는 sdk prefix 를 사용해야 함.
            .endpoint(targetEmail)
            .returnSubscriptionArn(true)
            .topicArn(targetTopicArn)
            .build()
    }

}
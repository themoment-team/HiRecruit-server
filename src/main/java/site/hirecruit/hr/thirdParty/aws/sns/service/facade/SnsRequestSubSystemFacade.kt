package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import software.amazon.awssdk.services.sns.model.ConfirmSubscriptionRequest
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.ListTopicsRequest
import software.amazon.awssdk.services.sns.model.SubscribeRequest

interface SnsRequestSubSystemFacade {
    fun createTopicRequest(topicName: String): CreateTopicRequest
    fun createListTopicRequest() : ListTopicsRequest
    fun createConfirmSubscriptionRequest(subscriptionToken: String, topicArn: String) : ConfirmSubscriptionRequest
    fun createSubscribeRequest(targetEmail: String, targetTopicArn: String) : SubscribeRequest
}
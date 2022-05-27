package site.hirecruit.hr.thirdParty.aws.sns.service.facade

import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.*

interface SnsClientSubSystemFacade {
    fun createTopic(topicRequest: CreateTopicRequest, snsClient: SnsClient) : CreateTopicResponse
    fun getAllTopicsAsList(listTopicRequest: ListTopicsRequest, snsClient: SnsClient): ListTopicsResponse
    fun subscribeEmail(subscribeRequest: SubscribeRequest, snsClient: SnsClient) : String
    fun isAlreadyConfirm(confirmSubscriptionRequest: ConfirmSubscriptionRequest, snsClient: SnsClient) : String
}
package site.hirecruit.hr.thirdParty.aws.sns.service

import software.amazon.awssdk.services.sns.model.CreateTopicResponse
import software.amazon.awssdk.services.sns.model.Topic

interface SnsTopicFactoryService {
    fun createTopic(topicName: String) : CreateTopicResponse
    fun displayAllTopics() : MutableList<Topic>
    fun subTopicByEmail(email: String, topicArn: String) : String
    fun isClientConfirmSub(subscriptionToken: String, topicArn: String) : String
}
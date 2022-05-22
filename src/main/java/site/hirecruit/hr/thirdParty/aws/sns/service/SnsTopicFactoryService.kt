package site.hirecruit.hr.thirdParty.aws.sns.service

import software.amazon.awssdk.services.sns.model.Topic

interface SnsTopicFactoryService {
    fun createTopic(topicName: String)
    fun displayAllTopics() : MutableList<Topic>
}
package site.hirecruit.hr.thirdParty.aws.sns.service

interface SnsTopicFactoryService {
    fun createTopic(topicName: String)
}
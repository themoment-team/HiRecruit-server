package site.hirecruit.hr.thirdParty.aws.sns.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsTopicSubSystemFacade
import software.amazon.awssdk.services.sns.model.CreateTopicRequest

/**
 * sns topic을 생성해주는 서비스
 *
 * @author 전지환
 * @since 1.0.0
 */
@Service
class SnsTopicFactoryServiceImpl(
    private val credentialService: CredentialService,
    private val snsTopicSubSystemFacade: SnsTopicSubSystemFacade
) : SnsTopicFactoryService{

    /**
     * aws sns topic을 생성해주는 서비스
     *
     * @see CreateTopicRequest.name - Constraints: topicName must be ASCII 0 ~ 256
     * @see SnsTopicSubSystemFacade.servingTopicRequestToSnsClient - aws-api가 직접적으로 로직을 처리 함
     */
    override fun createTopic(topicName: String) {

        // topicRequest 생성
        val topicRequest = snsTopicSubSystemFacade.createTopicRequest(topicName)

        // topicRequest를 aws-sns-api가 처리하도록 serving 함.
        snsTopicSubSystemFacade.servingTopicRequestToSnsClient(topicRequest, credentialService.getSnsClient())

    }

    override fun displayAllTopics() {
        val listTopicRequest = snsTopicSubSystemFacade.createListTopicRequest()
        snsTopicSubSystemFacade.getAllTopicsAsList(listTopicRequest, credentialService.getSnsClient())
    }

}
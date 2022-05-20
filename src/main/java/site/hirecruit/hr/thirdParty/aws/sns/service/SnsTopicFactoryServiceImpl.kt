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
     * @see CreateTopicRequest.name - Constraints
     */
    override fun createTopic(topicName: String) {

        // topicRequest 생성
        val topicRequest = snsTopicSubSystemFacade.createTopicRequest(topicName)

        // topic 생성
        val createTopic = credentialService.getSnsClient().createTopic(topicRequest)

        // topic이 성공적으로 생성됐는지 assertion
        snsTopicSubSystemFacade.sdkHealthChecker(createTopic)
    }

}
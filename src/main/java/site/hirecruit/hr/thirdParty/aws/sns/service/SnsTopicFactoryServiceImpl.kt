package site.hirecruit.hr.thirdParty.aws.sns.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsTopicSubSystemFacade
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.CreateTopicResponse
import software.amazon.awssdk.services.sns.model.Topic

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
     * @throws NoSuchElementException - 요청은 isSuccessful 이지만 topic 결과가 없을 때.
     */
    override fun createTopic(topicName: String): CreateTopicResponse {

        // topicRequest 생성
        val topicRequest = snsTopicSubSystemFacade.createTopicRequest(topicName)

        // topicRequest를 aws-sns-api가 처리하도록 serving 함.
        return snsTopicSubSystemFacade.servingTopicRequestToSnsClient(topicRequest, credentialService.getSnsClient())
            ?: throw NoSuchElementException("요청하신 createTopic 결과: CreateTopicResponse가 존재하지 않습니다.")

    }

    /**
     * aws sns 모든 topic을 가져오는 서비스
     *
     * @throws NoSuchElementException getAllTopicsAsList로 반환된 결과가 비어있는, 없는 공간일 때.
     * @return ListTopicResponse - MutableList<T> 읽기, 쓰기가 가능한 객체
     */
    override fun displayAllTopics() : MutableList<Topic> {
        val listTopicRequest = snsTopicSubSystemFacade.createListTopicRequest()

        return snsTopicSubSystemFacade.getAllTopicsAsList(listTopicRequest, credentialService.getSnsClient())?.topics()
            ?: throw NoSuchElementException("요청하신 getAllTopics의 결과: topics element가 존재하지 않습니다.")
    }

}
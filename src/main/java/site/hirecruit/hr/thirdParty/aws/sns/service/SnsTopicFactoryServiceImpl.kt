package site.hirecruit.hr.thirdParty.aws.sns.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsClientSubSystemFacade
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsRequestSubSystemFacade
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
    private val snsRequestSubSystemFacade: SnsRequestSubSystemFacade,
    private val snsClientSubSystemFacade: SnsClientSubSystemFacade
) : SnsTopicFactoryService {

    /**
     * aws sns topic을 생성해주는 서비스
     *
     * @see CreateTopicRequest.name - Constraints: topicName must be ASCII 0 ~ 256
     * @see SnsClientSubSystemFacade.createTopic - aws-api가 직접적으로 로직을 처리 함
     * @throws NoSuchElementException - 요청은 isSuccessful 이지만 topic 결과가 없을 때.
     */
    override fun createTopic(topicName: String): CreateTopicResponse {

        // topicRequest 생성
        val topicRequest = snsRequestSubSystemFacade.createTopicRequest(topicName)

        // topicRequest를 aws-sns-api가 처리하도록 serving 함.
        return snsClientSubSystemFacade.createTopic(topicRequest, credentialService.getSnsClient())
            ?: throw NoSuchElementException("요청하신 createTopic 결과: CreateTopicResponse가 존재하지 않습니다.")

    }

    /**
     * aws sns 모든 topic을 가져오는 서비스
     *
     * @throws NoSuchElementException getAllTopicsAsList로 반환된 결과가 비어있는, 없는 공간일 때.
     * @return ListTopicResponse - MutableList<T> 읽기, 쓰기가 가능한 객체
     */
    override fun displayAllTopics() : MutableList<Topic> {
        val listTopicRequest = snsRequestSubSystemFacade.createListTopicRequest()

        return snsClientSubSystemFacade.getAllTopicsAsList(listTopicRequest, credentialService.getSnsClient())?.topics()
            ?: throw NoSuchElementException("요청하신 getAllTopics의 결과: topics element가 존재하지 않습니다.")
    }

    /**
     * email 주소로 amazon sns topic 구독
     *
     * @param email 등록하고자 하는 email
     * @param topicArn 대상 topicArn
     * @see SnsClientSubSystemFacade.subscribeEmail 값을 최종적으로 리턴 함.
     * @return subscriptionArn - 구독을 식별할 수 있는 subscriptionArn
     */
    override fun subTopicByEmail(email: String, topicArn: String): String {
        val subscribeRequest = snsRequestSubSystemFacade.createSubscribeRequest(email, topicArn)

        return snsClientSubSystemFacade.subscribeEmail(subscribeRequest, credentialService.getSnsClient())
    }

    /**
     * sub client가 topic에 대해 메시지 수신을 허용했는지 확인하는 로직
     *
     * @param subscriptionToken sub로 등록할 때 반환한 결과 값
     * @param topicArn sub로 등록한 targetTopicArn
     * @return subscriptionToken - confirm 된 subscriptionToken
     */
    override fun isClientConfirmSub(subscriptionToken: String, topicArn: String) : String {
        val confirmSubscriptionRequest =
            snsRequestSubSystemFacade.createConfirmSubscriptionRequest(subscriptionToken, topicArn)

        return snsClientSubSystemFacade.isAlreadyConfirm(confirmSubscriptionRequest, credentialService.getSnsClient())
    }

}
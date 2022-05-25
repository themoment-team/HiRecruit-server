package site.hirecruit.hr.thirdParty.aws.sns.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.any
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.impl.SnsClientSubSystemFacadeImpl
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.impl.SnsRequestSubSystemFacadeImpl
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.ListTopicsResponse
import software.amazon.awssdk.services.sns.model.Topic

@LocalTest
class SnsTopicFactoryServiceImplTest{


    @Test
    @DisplayName("SnsClient의 sns에 topic이 정상적으로 생성된다")
    fun snsTopicCreateSuccessful(){
        // mocking
        val snsClient: SnsClient = mockk()
        val credentialService: CredentialService = mockk()
        val snsClientSubSystemFacadeImpl: SnsClientSubSystemFacadeImpl = mockk()
        val snsRequestSubSystemFacadeImpl: SnsRequestSubSystemFacadeImpl = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsRequestSubSystemFacadeImpl, snsClientSubSystemFacadeImpl)

        /**
         * 1. topicReqest는 mockTopicRequest를 리턴한다.
         * 2. 가짜 snsClient를 리턴한다. (실제 snsClient를 사용하지 않는다)
         * 2. snsClient는 정상적으로 true를 리턴한다.
         * 3. snsClient는 HttpStatus isSuccessful를 리턴한다.
         */
        every { snsRequestSubSystemFacadeImpl.createTopicRequest(any()) }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsClientSubSystemFacadeImpl.createTopic(any(), snsClient) }.returns(any())

        // When:: mockSnsClient는 아무런 sns topic 도 생성하지 못할것을 확신한다.
        assertDoesNotThrow {
            snsTopicFactoryService.createTopic(RandomString.make(5))
        }

        // Then
        verify(exactly = 1) { snsRequestSubSystemFacadeImpl.createTopicRequest(any()) }
        verify(exactly = 1) { credentialService.getSnsClient() }
        verify(exactly = 1) { snsClientSubSystemFacadeImpl.createTopic(any(), snsClient) }
    }

    @Test
    @DisplayName("SnsClient에 등록된 topic들이 모두 조회된다.")
    fun snsTopicsWereDisplay(){
        // Given:: mocking
        val snsClient : SnsClient = mockk()
        val credentialService : CredentialService = mockk()
        val snsClientSubSystemFacadeImpl : SnsClientSubSystemFacadeImpl = mockk()
        val snsRequestSubSystemFacadeImpl: SnsRequestSubSystemFacadeImpl = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsRequestSubSystemFacadeImpl, snsClientSubSystemFacadeImpl)
        val listTopicsResponse : ListTopicsResponse = mockk()
        val topicsList : List<Topic> = mockk()

        // Given:: stubs
        every { snsRequestSubSystemFacadeImpl.createListTopicRequest() }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsClientSubSystemFacadeImpl.getAllTopicsAsList(any(), snsClient) }.returns(listTopicsResponse)
        every { snsClientSubSystemFacadeImpl.getAllTopicsAsList(any(), snsClient).topics() }.returns(topicsList)

        // when:: mockSnsClient는 아무런 sns topic 도 가지지 않을 것을 확신한다.
        assertDoesNotThrow {
            snsTopicFactoryService.displayAllTopics()
        }

        // Then
        verify(exactly = 1) { snsRequestSubSystemFacadeImpl.createListTopicRequest() }
        verify(exactly = 1) { snsClientSubSystemFacadeImpl.getAllTopicsAsList(any(), any()) }
    }

    @Test
    @DisplayName("topicArn에 알맞는 topic에 email이 정상적으로 등록된다")
    fun subEmailToTopicArnSuccessful(){
        // Given:: mocking
        val snsClient : SnsClient = mockk()
        val credentialService : CredentialService = mockk()
        val snsClientSubSystemFacadeImpl : SnsClientSubSystemFacadeImpl = mockk()
        val snsRequestSubSystemFacadeImpl: SnsRequestSubSystemFacadeImpl = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsRequestSubSystemFacadeImpl, snsClientSubSystemFacadeImpl)

        // Given:: stubs
        every { snsRequestSubSystemFacadeImpl.createSubscribeRequest(any(), any()) }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsClientSubSystemFacadeImpl.subscribeEmail(any(), snsClient) }.returns(any())

        // When
        assertDoesNotThrow {
            snsTopicFactoryService.subTopicByEmail(RandomString.make(5), RandomString.make(5))
        }

        // Then
        verify(exactly = 1) { snsRequestSubSystemFacadeImpl.createSubscribeRequest(any(), any()) }
        verify(exactly = 1) { snsClientSubSystemFacadeImpl.subscribeEmail(any(), any()) }
    }

    @Test
    @DisplayName("subArn 을 가지고 topicArn 에 대해 sns 메시지를 수신하기를 원하는지 확인한다.")
    fun subArnConfirmCheck(){
        // Given:: mocking
        val snsClient : SnsClient = mockk()
        val credentialService : CredentialService = mockk()
        val snsClientSubSystemFacadeImpl : SnsClientSubSystemFacadeImpl = mockk()
        val snsRequestSubSystemFacadeImpl: SnsRequestSubSystemFacadeImpl = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsRequestSubSystemFacadeImpl, snsClientSubSystemFacadeImpl)

        every { snsRequestSubSystemFacadeImpl.createConfirmSubscriptionRequest(any(), any()) }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsClientSubSystemFacadeImpl.isAlreadyConfirm(any(), snsClient) }.returns(any())

        // when
        assertDoesNotThrow {
            snsTopicFactoryService.isClientConfirmSub(RandomString.make(5), RandomString.make(5))
        }

        // Then
        verify(exactly = 1) {snsRequestSubSystemFacadeImpl.createConfirmSubscriptionRequest(any(), any())}
        verify(exactly = 1) {snsClientSubSystemFacadeImpl.isAlreadyConfirm(any(), any())}
    }
}
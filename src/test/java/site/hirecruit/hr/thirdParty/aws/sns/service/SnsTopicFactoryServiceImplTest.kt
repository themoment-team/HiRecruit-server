package site.hirecruit.hr.thirdParty.aws.sns.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.thirdParty.aws.service.CredentialService
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsTopicSubSystemFacade
import software.amazon.awssdk.services.sns.SnsClient

@LocalTest
class SnsTopicFactoryServiceImplTest{


    @Test
    @DisplayName("SnsClient의 sns에 topic이 정상적으로 생성된다")
    fun snsTopicCreateSuccessful(){
        // mocking
        val snsClient: SnsClient = mockk()
        val credentialService: CredentialService = mockk()
        val snsTopicSubSystemFacade: SnsTopicSubSystemFacade = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsTopicSubSystemFacade)

        /**
         * 1. topicReqest는 mockTopicRequest를 리턴한다.
         * 2. 가짜 snsClient를 리턴한다. (실제 snsClient를 사용하지 않는다)
         * 2. snsClient는 정상적으로 true를 리턴한다.
         * 3. snsClient는 HttpStatus isSuccessful를 리턴한다.
         */
        every { snsTopicSubSystemFacade.createTopicRequest(any()) }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsTopicSubSystemFacade.servingTopicRequestToSnsClient(any(), snsClient) }.returns(any())

        // When:: mockSnsClient는 아무런 sns topic 도 생성하지 못할것을 확신한다.
        assertThrows<NoSuchElementException> {
            snsTopicFactoryService.createTopic(RandomString.make(5))
        }

        // Then
        verify(exactly = 1) { snsTopicSubSystemFacade.createTopicRequest(any()) }
        verify(exactly = 1) { credentialService.getSnsClient() }
        verify(exactly = 1) { snsTopicSubSystemFacade.servingTopicRequestToSnsClient(any(), snsClient) }
    }

    @Test
    @DisplayName("SnsClient에 등록된 topic들이 모두 조회된다.")
    fun snsTopicsWereDisplay(){
        // Given:: mocking
        val snsClient : SnsClient = mockk()
        val credentialService : CredentialService = mockk()
        val snsTopicSubSystemFacade : SnsTopicSubSystemFacade = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsTopicSubSystemFacade)

        // Given:: stubs
        every { snsTopicSubSystemFacade.createListTopicRequest() }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsTopicSubSystemFacade.getAllTopicsAsList(any(), credentialService.getSnsClient()) }.returns(any())

        // when:: mockSnsClient는 아무런 sns topic 도 가지지 않을 것을 확신한다.
        assertThrows<NoSuchElementException> {
            snsTopicFactoryService.displayAllTopics()
        }

        // Then
        verify(exactly = 1) { snsTopicSubSystemFacade.createListTopicRequest() }
        verify(exactly = 1) { credentialService.getSnsClient() }
    }

    @Test
    @DisplayName("topicArn에 알맞는 topic에 email이 정상적으로 등록된다")
    fun subEmailToTopicArnSuccessful(){
        // Given:: mocking
        val snsClient : SnsClient = mockk()
        val credentialService : CredentialService = mockk()
        val snsTopicSubSystemFacade : SnsTopicSubSystemFacade = mockk()
        val snsTopicFactoryService = SnsTopicFactoryServiceImpl(credentialService, snsTopicSubSystemFacade)

        // Given:: stubs
        every { snsTopicSubSystemFacade.createSubscribeRequest(any(), any()) }.returns(any())
        every { credentialService.getSnsClient() }.returns(snsClient)
        every { snsTopicSubSystemFacade.subscribeEmail(any(), snsClient) }.returns(any())

        // When
        assertDoesNotThrow {
            snsTopicFactoryService.subTopicByEmail(RandomString.make(5), RandomString.make(5))
        }

        // Then
        verify(exactly = 1) { snsTopicSubSystemFacade.createSubscribeRequest(any(), any()) }
        verify(exactly = 1) { snsTopicSubSystemFacade.subscribeEmail(any(), any()) }
    }
}
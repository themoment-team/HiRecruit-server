package site.hirecruit.hr.thirdParty.aws.sns.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
        every { snsTopicSubSystemFacade.servingTopicRequestToSnsClient(any(), snsClient) }.returns(true)

        // When
        snsTopicFactoryService.createTopic(RandomString.make(5))

        // Then
        verify(exactly = 1) { snsTopicSubSystemFacade.createTopicRequest(any()) }
        verify(exactly = 1) { credentialService.getSnsClient() }
        verify(exactly = 1) { snsTopicSubSystemFacade.servingTopicRequestToSnsClient(any(), snsClient) }
    }
}
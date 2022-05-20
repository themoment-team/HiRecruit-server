package site.hirecruit.hr.thirdParty.aws.sns.service

import io.mockk.every
import io.mockk.mockk
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.thirdParty.aws.sns.service.facade.SnsTopicSubSystemFacade
import software.amazon.awssdk.services.sns.model.CreateTopicRequest

@SpringBootTest
@LocalTest
class SnsTopicFactoryServiceImplTest{


    @Test
    @DisplayName("sns topic이 정상적으로 생성된다")
    fun snsTopicCreateSuccessful(
        @Autowired snsTopicFactoryService: SnsTopicFactoryService

    ){
        // mocking
        val snsTopicSubSystemFacade: SnsTopicSubSystemFacade = mockk()
        val createTopicRequest: CreateTopicRequest = mockk()

        // Given
        every { snsTopicSubSystemFacade.createTopicRequest(RandomString.make(5)) }.returns(mockTopicRequest())

        // When ~ Then
        assertDoesNotThrow {
            snsTopicFactoryService.createTopic(RandomString.make(5))
        }
    }

    private fun mockTopicRequest() : CreateTopicRequest {
        return CreateTopicRequest.builder()
            .name(RandomString.make(5))
            .build()
    }
}
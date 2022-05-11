package site.hirecruit.hr.domain.auth.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User
import site.hirecruit.hr.domain.auth.service.impl.OAuth2ProcessorFacadeImpl
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import kotlin.random.Random

@ActiveProfiles("local")
internal class OAuth2ProcessorFacadeImplTest{

    private fun makeOAuth2Attribute() : OAuthAttributes{
        val attributes = mapOf<String, Any>(
            "id" to Random.nextInt(8),
            "name" to RandomString.make(8),
            "email" to "${RandomString.make(10)}${RandomString.make(6)}.${RandomString.make(3)}}",
            "avatar_url" to RandomString.make()
        )
        return OAuthAttributes.of(
            registrationId = "github",
            userNameAttributeName = "id",
            attributes = attributes
        )
    }

    @Test
    @DisplayName("만약 유저가 처음 OAuth2로그인을 한다면?")
    fun test_processor_만약_첫_사용자라면(){
        // 만약 유저가 처음으로 OAuth2 인증을 진행한다면
        // 1. workerRepository.existsByGithubId(github_id)를 통해 처음 회원가입 하는 여부를 확인한다.
        // 2. 첫 OAuth2로그인이라면 userRegistrationService.registration(oAuth2Attribute)를 실행한다.
        // 3, 그 후 userAuthService.authentication(oAuth2Attribute)를 실행한다.

        // Given
        val oAuth2Attribute : OAuthAttributes = makeOAuth2Attribute()
        val workerRepository : WorkerRepository = mockk()
        val userRegistrationService : UserRegistrationService = mockk()
        val userAuthService : UserAuthService = mockk()

        val oAuth2ProcessorFacadeImpl =
            OAuth2ProcessorFacadeImpl(workerRepository, userRegistrationService, userAuthService)

        every { workerRepository.existsByGithubId(oAuth2Attribute.id) } answers { false }
        every { userRegistrationService.registration(oAuth2Attribute) } answers { Any() }
        every { userAuthService.authentication(oAuth2Attribute) } answers {
            User(oAuth2Attribute.id, oAuth2Attribute.name, oAuth2Attribute.email!!, oAuth2Attribute.profileImgUri, WorkerEntity.Role.GUEST)
        }

        // when
        oAuth2ProcessorFacadeImpl.process(oAuth2Attribute)

        // then
        verify(exactly = 1) { workerRepository.existsByGithubId(oAuth2Attribute.id) }
        verify(exactly = 1) { userRegistrationService.registration(oAuth2Attribute) }
        verify(exactly = 1) { userAuthService.authentication(oAuth2Attribute) }
    }
}
package site.hirecruit.hr.domain.auth.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.impl.OAuth2ProcessorFacadeImpl
import site.hirecruit.hr.domain.test_util.LocalTest
import kotlin.random.Random

@LocalTest
internal class OAuth2ProcessorFacadeImplTest{

    private fun makeOAuth2Attribute() : OAuthAttributes{
        val attributes = mapOf<String, Any>(
            "id" to Random.nextInt(8),
            "login" to RandomString.make(5),
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
        // 1. userRepository.existsByGithubId(github_id)를 통해 처음 회원가입 하는 여부를 확인한다.
        // 2. 첫 OAuth2로그인이라면 tempUserRegistrationService.registration(oAuth2Attribute)를 실행한다.
        // 3, 그 후 userAuthService.authentication(oAuth2Attribute)를 실행한다.

        // Given
        val oAuth2Attribute : OAuthAttributes = makeOAuth2Attribute()
        val userRepository : UserRepository = mockk()
        val tempUserRegistrationService : TempUserRegistrationService = mockk()
        val userAuthService : UserAuthService = mockk()

        val oAuth2ProcessorFacadeImpl =
            OAuth2ProcessorFacadeImpl(userRepository, tempUserRegistrationService, userAuthService)

        every { userRepository.existsByGithubId(oAuth2Attribute.id) } answers { false }
        every { tempUserRegistrationService.registration(oAuth2Attribute) } answers { Any() }
        every { userAuthService.authentication(oAuth2Attribute) } answers {
            AuthUserInfo(oAuth2Attribute.id, oAuth2Attribute.attributes["login"] as String, oAuth2Attribute.name!!, oAuth2Attribute.email!!, oAuth2Attribute.profileImgUri, Role.GUEST)
        }

        // when
        oAuth2ProcessorFacadeImpl.process(oAuth2Attribute)

        // then
        verify(exactly = 1) { userRepository.existsByGithubId(oAuth2Attribute.id) }
        verify(exactly = 1) { tempUserRegistrationService.registration(oAuth2Attribute) }
        verify(exactly = 1) { userAuthService.authentication(oAuth2Attribute) }
    }
}
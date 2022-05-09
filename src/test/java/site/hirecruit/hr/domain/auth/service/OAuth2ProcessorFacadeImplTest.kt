package site.hirecruit.hr.domain.auth.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
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
        // 1. 회원이 처음 로그인을 한다면 회원가입 로직(userRegistrationService.registration(oauthAttributes))가 실행된 후,
        // 2, 인증이 진행된다.

        // Given
        val oAuth2Attribute = makeOAuth2Attribute()


    }
}
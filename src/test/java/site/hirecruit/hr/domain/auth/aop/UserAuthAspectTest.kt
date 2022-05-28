package site.hirecruit.hr.domain.auth.aop

import io.mockk.every
import io.mockk.mockk
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.mock.web.MockHttpSession
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.service.UserAuthService
import site.hirecruit.hr.global.data.SessionAttribute
import kotlin.random.Random

internal class UserAuthAspectTest{

    private fun makeOAuth2Attributes() : OAuthAttributes {
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

    @Test @DisplayName("proxy.authentication를 실행 후 UserAuthAspect가 Session에 반환값을 잘 실행하는지")
    fun userAuthAspectTest(){
        // Given
        val httpSession = MockHttpSession()

        val oAuth2Attributes = makeOAuth2Attributes()
        val proxyReturnValue = AuthUserInfo(
            githubId = oAuth2Attributes.id,
            email = null,
            name = oAuth2Attributes.name,
            profileImgUri = oAuth2Attributes.profileImgUri,
            role = Role.GUEST
        )

        val userAuthService: UserAuthService = mockk() // proxy
        val factory = AspectJProxyFactory(userAuthService)
        every { userAuthService.authentication(oAuth2Attributes) } answers { proxyReturnValue }
        factory.addAspect(UserAuthAspect(httpSession))
        val proxy = factory.getProxy<UserAuthService>()

        // when
        proxy.authentication(oAuth2Attributes)

        // then
        val sessionAuthUserInfo = httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName)
        Assertions.assertEquals(proxyReturnValue, sessionAuthUserInfo)
    }
}
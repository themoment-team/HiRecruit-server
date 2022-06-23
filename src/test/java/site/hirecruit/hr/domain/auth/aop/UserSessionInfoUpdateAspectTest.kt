package site.hirecruit.hr.domain.auth.aop

import io.mockk.every
import io.mockk.mockk
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.mock.web.MockHttpSession
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.service.UserAuthService
import site.hirecruit.hr.domain.auth.service.UserRegistrationRollbackService
import site.hirecruit.hr.global.data.SessionAttribute
import kotlin.random.Random

internal class UserSessionInfoUpdateAspectTest{

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
    fun userAuthAspect_UserAuthService_Test(){
        // Given
        val httpSession = MockHttpSession()

        val oAuth2Attributes = makeOAuth2Attributes()
        val proxyReturnValue = AuthUserInfo(
            githubId = oAuth2Attributes.id,
            githubLoginId = RandomString.make(5),
            email = null,
            name = oAuth2Attributes.name!!,
            profileImgUri = oAuth2Attributes.profileImgUri,
            role = Role.GUEST
        )

        val userAuthService: UserAuthService = mockk() // proxy
        val factory = AspectJProxyFactory(userAuthService)
        every { userAuthService.authentication(oAuth2Attributes) } answers { proxyReturnValue }
        factory.addAspect(UserSessionInfoUpdateAspect(httpSession))
        val proxy = factory.getProxy<UserAuthService>()

        // when
        proxy.authentication(oAuth2Attributes)

        // then
        val sessionAuthUserInfo = httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName)
        Assertions.assertEquals(proxyReturnValue, sessionAuthUserInfo)
    }

    @Test @DisplayName("proxy.rollback를 실행 후 UserAuthAspect가 Session에 반환값을 잘 실행하는지")
    fun userAuthAspect_UserRegistrationRollbackService_Test(){
        // Given
        val httpSession = MockHttpSession()

        val authUserInfo = AuthUserInfo(
            githubId = Random.nextLong(),
            githubLoginId = RandomString.make(5),
            name = RandomString.make(5),
            email = RandomString.make(5),
            profileImgUri = RandomString.make(10),
            role = Role.CLIENT
        )
        val proxyReturnValue = AuthUserInfo(
            githubId = authUserInfo.githubId,
            githubLoginId = authUserInfo.githubLoginId,
            email = authUserInfo.email,
            name = authUserInfo.name,
            profileImgUri = authUserInfo.profileImgUri,
            role = Role.GUEST
        )

        val userAuthService: UserRegistrationRollbackService = mockk() // proxy
        val factory = AspectJProxyFactory(userAuthService)
        every { userAuthService.rollback(authUserInfo) } answers { proxyReturnValue }
        factory.addAspect(UserSessionInfoUpdateAspect(httpSession))
        val proxy = factory.getProxy<UserRegistrationRollbackService>()

        // when
        proxy.rollback(authUserInfo)

        // then
        val sessionAuthUserInfo = httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName)
        Assertions.assertEquals(proxyReturnValue, sessionAuthUserInfo)
    }

}
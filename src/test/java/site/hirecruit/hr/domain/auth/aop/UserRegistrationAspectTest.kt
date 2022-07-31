package site.hirecruit.hr.domain.auth.aop

import io.mockk.*
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.mock.web.MockHttpSession
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.service.SecurityContextAccessService
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.user.aop.UserRegistrationAspect
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationDto
import site.hirecruit.hr.domain.user.service.UserRegistrationService
import site.hirecruit.hr.global.data.SessionAttribute
import kotlin.random.Random

@LocalTest
internal class UserRegistrationAspectTest{

    private lateinit var httpSession: MockHttpSession;
    private lateinit var tempUserInfo: AuthUserInfo;

    @BeforeEach
    fun beforeRegistrationInSession(){
        httpSession = MockHttpSession()
        tempUserInfo = AuthUserInfo(
            githubId = Random.nextLong(),
            githubLoginId = RandomString.make(5),
            name = RandomString.make(5),
            email = null,
            profileImgUri = RandomString.make(15),
            role = Role.GUEST
        )
        httpSession.setAttribute(SessionAttribute.AUTH_USER_INFO.attributeName, tempUserInfo)
    }

    @Test @DisplayName("afterRegistrationMethod test")
    fun afterRegistrationMethodTest(){
        // given:: Aspect가 실행될 proxy
        val userRegistrationService: UserRegistrationService<RegularUserRegistrationDto> = mockk()
        val tempUserRepository: TempUserRepository = spyk()

        val securityContextAccessService: SecurityContextAccessService = mockk()
        every { securityContextAccessService.updateAuthentication(any()) } just Runs // security context authenticate update logic

        // given:: UserRegistrationService에 Aspect추가 및 proxy 가져오기
        val factory = AspectJProxyFactory(userRegistrationService)
        factory.addAspect(UserRegistrationAspect(tempUserRepository, httpSession, securityContextAccessService))
        val proxy = factory.getProxy<UserRegistrationService<RegularUserRegistrationDto>>()

        // given:: Aspect가 실행될 proxy
        val userRegistrationDto: RegularUserRegistrationDto = mockk(relaxed = true)
        val proxyReturnValue = AuthUserInfo(
            githubId = tempUserInfo.githubId,
            githubLoginId = tempUserInfo.githubLoginId,
            name = userRegistrationDto.userRegistrationInfo.name,
            email = userRegistrationDto.userRegistrationInfo.email,
            profileImgUri = tempUserInfo.profileImgUri,
            role = Role.GUEST
        )

        // proxy(userRegistrationService) 객체는 proxyReturnValue를 반환한다.
        every { userRegistrationService.registration(userRegistrationDto) } answers { proxyReturnValue }

        // when AOP에 사용된 proxy 객체의 AOP가 적용된 메서드를 실행한다.
        proxy.registration(userRegistrationDto)

        // then
        verify(exactly = 1) { securityContextAccessService.updateAuthentication(any()) }
        verify(exactly = 1) { tempUserRepository.deleteById(tempUserInfo.githubId) }
        val sessionAuthUserInfo = httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName) as AuthUserInfo
        Assertions.assertEquals(proxyReturnValue, sessionAuthUserInfo, "proxy가 반환한 정보가 session에 반환되지 않았으므로 Aspect setSession로직 확인 요망")
    }
}
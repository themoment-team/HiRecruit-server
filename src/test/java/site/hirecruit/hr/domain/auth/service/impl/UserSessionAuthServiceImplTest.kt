package site.hirecruit.hr.domain.auth.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.entity.TempUserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.repository.UserRepository
import kotlin.random.Random

internal class UserSessionAuthServiceImplTest{

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

    private val tempUserRepository: TempUserRepository = mockk()

    @DisplayName("TempUser(임시유저)가 ")
    @Nested
    inner class TempUserAuthTest{

        private val userRepository: UserRepository = spyk()

        @Test @DisplayName("인증을 받는다면?")
        fun `TempUser가 인증을 받는다면`(){
            // Given
            val oAuth2Attributes = makeOAuth2Attributes()

            val userAuthServiceImpl = UserSessionAuthServiceImpl(userRepository, tempUserRepository)

            every { tempUserRepository.existsById(oAuth2Attributes.id) } answers {true} // 임시회원이 존재한다면
            every { tempUserRepository.findByIdOrNull(oAuth2Attributes.id) } answers {
                TempUserEntity(
                    githubId = oAuth2Attributes.id,
                    name = oAuth2Attributes.name,
                    profileImgUri = oAuth2Attributes.profileImgUri
                )
            }

            // when
            val userAuthInfo = userAuthServiceImpl.authentication(oAuth2Attributes)

            // then
            verify(exactly = 1) {tempUserRepository.findByIdOrNull(oAuth2Attributes.id)}
            assertEquals(userAuthInfo.githubId, oAuth2Attributes.id)
            assertEquals(userAuthInfo.name, oAuth2Attributes.name)
            assertEquals(userAuthInfo.profileImgUri, oAuth2Attributes.profileImgUri)
            assertEquals(userAuthInfo.role, Role.GUEST)
        }

        @Test @DisplayName("인증을 받을 떄 인증정보가 조회되지 않는다면?")
        fun `인증을 받을 떄 정보를 가져올 수 없다면`(){
            // Given
            val oAuth2Attributes = makeOAuth2Attributes()
            val userAuthServiceImpl = UserSessionAuthServiceImpl(userRepository, tempUserRepository)

            every { tempUserRepository.existsById(oAuth2Attributes.id) } answers {true} // 임시회원이 존재한다면
            every { tempUserRepository.findByIdOrNull(oAuth2Attributes.id) } answers { null }

            // when then
            assertThrows<OAuth2AuthenticationException> ("임시 회원의 유효기간이 만료되었거나, 잘못된 회원 정보입니다."){
                userAuthServiceImpl.authentication(oAuth2Attributes)
            }
        }
    }

    @DisplayName("User(회원)가 ")
    @Nested
    inner class UserAuthTest{
        private val userRepository: UserRepository = mockk()

        @Test @DisplayName("인증을 받는다면?")
        fun `User가 인증을 받는다면`(){
            // Given
            val oAuth2Attributes = makeOAuth2Attributes()

            val userAuthServiceImpl = UserSessionAuthServiceImpl(userRepository, tempUserRepository)
            val authUserInfo = AuthUserInfo(
                githubId = oAuth2Attributes.id,
                name = oAuth2Attributes.name,
                email = "${RandomString.make(8)}@${RandomString.make(5)}.${RandomString.make(3)}",
                profileImgUri = oAuth2Attributes.profileImgUri,
                Role.CLIENT
            )

            every { tempUserRepository.existsById(oAuth2Attributes.id) } answers {false} // 임시회원이 존재한다면
            every { userRepository.findUserAndWorkerEmailByGithubId(oAuth2Attributes.id) } answers { authUserInfo }

            // when
            val authenticatedUserAuthInfo = userAuthServiceImpl.authentication(oAuth2Attributes)

            // then
            verify(exactly = 1) {userRepository.findUserAndWorkerEmailByGithubId(oAuth2Attributes.id)}
            assertEquals(authUserInfo, authenticatedUserAuthInfo)
        }

        @Test @DisplayName("인증을 받을 떄 인증정보가 조회되지 않는다면?")
        fun `User가 인증을 받을 떄 정보를 가져올 수 없다면`(){
            // Given
            val oAuth2Attributes = makeOAuth2Attributes()
            val userAuthServiceImpl = UserSessionAuthServiceImpl(userRepository, tempUserRepository)

            every { tempUserRepository.existsById(oAuth2Attributes.id) } answers {false} // 임시회원이 아니라면
            every { userRepository.findUserAndWorkerEmailByGithubId(oAuth2Attributes.id) } answers { null }

            // when then
            assertThrows<OAuth2AuthenticationException> ("해당 oauth정보로 회원을 찾을 수 없습니다. [githubId = '${oAuth2Attributes.id}']"){
                userAuthServiceImpl.authentication(oAuth2Attributes)
            }
        }
    }

}
package site.hirecruit.hr.domain.auth.service.impl

import io.mockk.*
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.TempUserRegistrationService
import site.hirecruit.hr.domain.user.service.UserRegistrationRollbackServiceImpl
import kotlin.random.Random

internal class UserRegistrationRollbackServiceImplTest{

    private val userRepository: UserRepository = mockk()
    private val tempUserRegistrationService: TempUserRegistrationService = mockk()

    private val userRegistrationRollbackServiceImpl = UserRegistrationRollbackServiceImpl(this.userRepository, this.tempUserRegistrationService)


    @Test
    internal fun rollbackTest(){
        val beforeRollbackAuthUserInfo = AuthUserInfo(
            githubId = Random.nextLong(),
            githubLoginId = RandomString.make(5),
            name = RandomString.make(5),
            email = RandomString.make(10),
            profileImgUri = RandomString.make(10),
            role = Role.WORKER
        )
        val userRollbackDataOAuthAttributes = OAuthAttributes.ofUserRollbackData(beforeRollbackAuthUserInfo)

        every { userRepository.deleteByGithubId(beforeRollbackAuthUserInfo.githubId) } just runs
        every { tempUserRegistrationService.registration(userRollbackDataOAuthAttributes) } just runs

        val afterRollbackAuthUserInfo = userRegistrationRollbackServiceImpl.rollback(beforeRollbackAuthUserInfo)

        verify(exactly = 1) { userRepository.deleteByGithubId(afterRollbackAuthUserInfo.githubId) }
        verify(exactly = 1) { tempUserRegistrationService.registration(userRollbackDataOAuthAttributes) }

        assertAll("롤백 전 AuthUserInfo와 롤백 후 AuthUserInfo는 githubId, email, profileImgUri에 대해 같은 값을 가진다.", {
            assertEquals(beforeRollbackAuthUserInfo.githubId, afterRollbackAuthUserInfo.githubId)
            assertEquals(beforeRollbackAuthUserInfo.email, afterRollbackAuthUserInfo.email)
            assertEquals(beforeRollbackAuthUserInfo.profileImgUri, afterRollbackAuthUserInfo.profileImgUri)
        })

        assertAll("롤백 전 AuthUserInfo와 롤백 후 AuthUserInfo는 role, name에 대해 다른 값을 가진다.", {
            assertNotEquals(afterRollbackAuthUserInfo.role, beforeRollbackAuthUserInfo.role)
            assertNotEquals(afterRollbackAuthUserInfo.name, beforeRollbackAuthUserInfo.name)
        })

        assertAll("롤백 후 AuthUserInfo의 name=\"임시유저\" role=GUEST를 만족해야 한다.", {
            assertEquals("임시유저", afterRollbackAuthUserInfo.name)
            assertEquals(Role.GUEST, afterRollbackAuthUserInfo.role)
        })
    }
}
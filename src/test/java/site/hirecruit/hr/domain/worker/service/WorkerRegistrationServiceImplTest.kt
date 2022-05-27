package site.hirecruit.hr.domain.worker.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.entity.UserEntity
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import kotlin.random.Random

internal class WorkerRegistrationServiceImplTest{

    private val userRepository: UserRepository = mockk()
    private val workerRepository: WorkerRepository = mockk()
    private val workerRegistrationService = WorkerRegistrationServiceImpl(userRepository, workerRepository)

    @Test @DisplayName("Worker Registration test")
    fun workerRegistrationTest(){
        //given
        val authUserInfo = makeAuthUserInfo()
        val registrationDto = makeRegistrationDto()
        val userEntity = UserEntity(
            githubId = authUserInfo.githubId,
            email = authUserInfo.email!!,
            name = authUserInfo.name,
            profileImgUri = authUserInfo.profileImgUri,
            role = authUserInfo.role
        )
        val workerEntity = WorkerEntity(
            company = registrationDto.company,
            location = registrationDto.location,
            giveLink = registrationDto.giveLink,
            introduction = registrationDto.introduction,
            devYear = registrationDto.devYear,
            user = userEntity
        )

        every { userRepository.findByGithubId(authUserInfo.githubId) } answers {userEntity}
        every { workerRepository.save(workerEntity) } answers {workerEntity}

        // when
        val registrationWorkerInfo = workerRegistrationService.registration(authUserInfo, registrationDto)

        // then
        verify(exactly = 1) { userRepository.findByGithubId(authUserInfo.githubId) }
        verify(exactly = 1) { workerRepository.save(workerEntity) }

        // then:: 입력값 AuthUserInfo와 WorkerDto.Registration의 값이 잘 저장되었는지 확인
        assertAll({
            assertEquals(authUserInfo.name, registrationWorkerInfo.name)
            assertEquals(authUserInfo.email, registrationWorkerInfo.email)
            assertEquals(authUserInfo.profileImgUri, registrationWorkerInfo.profileImgUri)
            assertEquals(registrationDto.company, registrationWorkerInfo.company)
            assertEquals(registrationDto.location, registrationWorkerInfo.location)
            assertEquals(registrationDto.introduction, registrationWorkerInfo.introduction)
            assertEquals(registrationDto.giveLink, registrationWorkerInfo.giveLink)
            assertEquals(registrationDto.devYear, registrationWorkerInfo.devYear)
        })
    }

    @Test
    fun `만약 Registration로직에서 User가 조회되지 않는다면`() {
        //given
        val authUserInfo = makeAuthUserInfo()
        val registrationDto = makeRegistrationDto()

        every { userRepository.findByGithubId(authUserInfo.githubId) } answers {null}

        assertThrows(IllegalStateException::class.java) {
            workerRegistrationService.registration(authUserInfo, registrationDto)
        }
    }

    private fun makeRegistrationDto() = WorkerDto.Registration(
        company = RandomString.make(7),
        location = RandomString.make(15),
        giveLink = RandomString.make(15),
        introduction = RandomString.make(15),
        devYear = Random.nextInt()
    )

    private fun makeAuthUserInfo() = AuthUserInfo(
        githubId = Random.nextLong(),
        name = RandomString.make(5),
        email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
        profileImgUri = RandomString.make(10),
        role = Role.UNAUTHENTICATED_EMAIL
    )
}
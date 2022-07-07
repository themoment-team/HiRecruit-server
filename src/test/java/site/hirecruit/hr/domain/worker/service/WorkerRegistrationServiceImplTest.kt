package site.hirecruit.hr.domain.worker.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import kotlin.random.Random

internal class WorkerRegistrationServiceImplTest{

    private val userRepository: UserRepository = mockk()
    private val workerRepository: WorkerRepository = mockk()
    private val companyRepository: CompanyRepository = mockk()
    private val workerRegistrationService = WorkerRegistrationServiceImpl(userRepository, workerRepository, companyRepository, mockk(relaxed = true))

    @Test @DisplayName("Worker Registration test")
    fun workerRegistrationTest(){
        //given
        val authUserInfo = makeAuthUserInfo()
        val registrationDto = makeRegistrationDto()
        val userEntity = UserEntity(
            githubId = authUserInfo.githubId,
            githubLoginId = authUserInfo.githubLoginId,
            email = authUserInfo.email!!,
            name = authUserInfo.name,
            profileImgUri = authUserInfo.profileImgUri,
            role = authUserInfo.role
        )
        val companyEntity = CompanyEntity(
            companyId = registrationDto.companyId,
            name = RandomString.make(10),
            location = RandomString.make(15),
            homepageUri = RandomString.make(15),
            companyImgUri = RandomString.make(15)
        )
        val workerEntity = WorkerEntity(
            workerId = null,
            giveLink = registrationDto.giveLink,
            introduction = registrationDto.introduction,
            devYear = registrationDto.devYear,
            position = registrationDto.position,
            user = userEntity,
            company = companyEntity
        )
        val savedWorkerEntity = WorkerEntity(
            workerId = Random.nextLong(),
            giveLink = registrationDto.giveLink,
            introduction = registrationDto.introduction,
            devYear = registrationDto.devYear,
            position = registrationDto.position,
            user = userEntity,
            company = companyEntity
        )

        every { userRepository.findByGithubId(authUserInfo.githubId) } answers {userEntity}
        every { workerRepository.save(workerEntity) } answers {savedWorkerEntity}
        every { companyRepository.findByIdOrNull(registrationDto.companyId) } answers {companyEntity}

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
            assertEquals(registrationDto.position, registrationWorkerInfo.position)
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
        _companyId = Random.nextLong(),
        giveLink = RandomString.make(15),
        introduction = RandomString.make(15),
        devYear = Random.nextInt(),
        position = RandomString.make(15),
    )

    private fun makeAuthUserInfo() = AuthUserInfo(
        githubId = Random.nextLong(),
        githubLoginId = RandomString.make(5),
        name = RandomString.make(5),
        email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
        profileImgUri = RandomString.make(10),
        role = Role.GUEST
    )
}
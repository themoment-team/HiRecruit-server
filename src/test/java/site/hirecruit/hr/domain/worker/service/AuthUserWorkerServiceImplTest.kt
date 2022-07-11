package site.hirecruit.hr.domain.worker.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import site.hirecruit.hr.test_config.DataJpaTestConfig
import kotlin.random.Random

@LocalTest
@Transactional
@DataJpaTest
@Import(DataJpaTestConfig::class)
@Disabled
class AuthUserWorkerServiceImplTest(
    @Autowired private val workerRepository: WorkerRepository,
    @Autowired private val companyRepository: CompanyRepository
){

    val authUserWorkerService = AuthUserWorkerServiceImpl(this.workerRepository, companyRepository)

    lateinit var userEntity: UserEntity; // 현재 기준이 되는 회원 createUser() 메서드 확인

    @BeforeEach
    fun createUser(@Autowired userRepository: UserRepository){
        val userEntity = UserEntity(
            githubId = Random.nextLong(),
            githubLoginId = RandomString.make(5),
            email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = RandomString.make(5),
            profileImgUri = RandomString.make(15),
            Role.WORKER
        )
        this.userEntity = userRepository.save(userEntity)
    }

    @Test
    fun `findWorkerByAuthUserInfo test`() {
        // given
        val companyEntity = createCompanyEntity()
        val workerEntity = createWorkerEntity(companyEntity)
        val authUserInfo = createAuthUserInfoByUserEntity()

        // when
        val myWorkerInfo = authUserWorkerService.findWorkerInfoByAuthUserInfo(authUserInfo)

        // then
        assertAll({
            assertEquals(authUserInfo.email, myWorkerInfo.email)
            assertEquals(authUserInfo.name, myWorkerInfo.name)
            assertEquals(authUserInfo.email, myWorkerInfo.email)
            assertEquals(authUserInfo.profileImgUri, myWorkerInfo.profileImgUri)
        })

        assertAll({
            assertEquals(workerEntity.introduction, workerEntity.introduction)
            assertEquals(workerEntity.devYear, workerEntity.devYear)
            assertEquals(workerEntity.giveLink, workerEntity.giveLink)
            assertEquals(companyEntity, workerEntity.company)
        })

        assertAll({
            assertEquals(companyEntity.companyId, myWorkerInfo.companyInfoDto.companyId)
            assertEquals(companyEntity.name, myWorkerInfo.companyInfoDto.name)
            assertEquals(companyEntity.location, myWorkerInfo.companyInfoDto.location)
            assertEquals(companyEntity.homepageUri, myWorkerInfo.companyInfoDto.homepageUri)
            assertEquals(companyEntity.companyImgUri, myWorkerInfo.companyInfoDto.companyImgUri)
        })
    }

    @Test @DisplayName("worker update test")
    internal fun workerUpdateTest() {
        // given
        val oldCompanyEntity = createCompanyEntity()
        val newCompanyEntity = createCompanyEntity()
        val workerEntity = workerEntityDeepCopy(createWorkerEntity(oldCompanyEntity))
        val updateDto = WorkerDto.Update(
            companyId = newCompanyEntity.companyId,
            introduction = RandomString.make(10),
            giveLink = RandomString.make(10),
            devYear = Random.nextInt(0, 30),
            position = RandomString.make(15),
        )
        val authUserInfo = createAuthUserInfoByUserEntity()

        // when
        authUserWorkerService.updateWorkerEntityByAuthUserInfo(authUserInfo, updateDto)

        //then
        val updatedWorkerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
        assertAll("업데이터 전에 조회한 WorkerEntity의 값과 Update후 조회된 WorkerEntity의 값은 달라야 한다.", {
            assertNotEquals(workerEntity.introduction, updatedWorkerEntity?.introduction)
            assertNotEquals(workerEntity.giveLink, updatedWorkerEntity?.giveLink)
            assertNotEquals(workerEntity.devYear, updatedWorkerEntity?.devYear)
            assertNotEquals(workerEntity.position, updatedWorkerEntity?.position)
            assertNotEquals(workerEntity.company, updatedWorkerEntity?.company)
        })

        assertAll("UpdateDto에 저장된, 값들은 update후 조회된 Entity에 반영되어야 한다.", {
            assertEquals(updateDto.companyId, updatedWorkerEntity?.company?.companyId)
            assertEquals(updateDto.introduction, updatedWorkerEntity?.introduction)
            assertEquals(updateDto.giveLink, updatedWorkerEntity?.giveLink)
            assertEquals(updateDto.devYear, updatedWorkerEntity?.devYear)
            assertEquals(updateDto.position, updatedWorkerEntity?.position)
            assertEquals(authUserInfo.githubId, updatedWorkerEntity?.user?.githubId)
        })
    }

    fun createCompanyEntity(): CompanyEntity = companyRepository.save(
        CompanyEntity(
            name = RandomString.make(10),
            location = RandomString.make(15),
            homepageUri = RandomString.make(15),
            companyImgUri = RandomString.make(10)
        )
    )
    fun createWorkerEntity(companyEntity: CompanyEntity) = workerRepository.save(
        WorkerEntity(
            introduction = RandomString.make(15),
            giveLink = RandomString.make(15),
            devYear = Random.nextInt(),
            user = this.userEntity,
            company = companyEntity
        )
    )

    private fun createAuthUserInfoByUserEntity() = AuthUserInfo(
        githubId = this.userEntity.githubId,
        githubLoginId = this.userEntity.githubLoginId,
        name = this.userEntity.name,
        email = this.userEntity.email,
        profileImgUri = this.userEntity.profileImgUri,
        role = this.userEntity.role
    )

    private fun workerEntityDeepCopy(workerEntity: WorkerEntity): WorkerEntity = WorkerEntity(
        position = workerEntity.position,
        introduction = workerEntity.introduction,
        giveLink = workerEntity.giveLink,
        devYear = workerEntity.devYear,
        user = userEntity,
        company = workerEntity.company
    )
}
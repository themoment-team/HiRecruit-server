package site.hirecruit.hr.domain.worker.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.entity.UserEntity
import site.hirecruit.hr.domain.auth.repository.UserRepository
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
class AuthUserWorkerServiceImplTest(
    @Autowired private val workerRepository: WorkerRepository
){

    val authUserWorkerService = AuthUserWorkerServiceImpl(this.workerRepository)

    lateinit var userEntity: UserEntity; // 현재 기준이 되는 회원 createUser() 메서드 확인

    @BeforeEach
    fun createUser(@Autowired userRepository: UserRepository){
        val userEntity = UserEntity(
            githubId = Random.nextLong(),
            email = "${RandomString.make(5)}@${RandomString.make(5)}.${RandomString.make(3)}",
            name = RandomString.make(5),
            profileImgUri = RandomString.make(15),
            Role.UNAUTHENTICATED_EMAIL
        )
        this.userEntity = userRepository.save(userEntity)
    }

    @Test
    fun `findWorkerByAuthUserInfo test`() {
        // given
        val workerEntity = createWorkerEntity()
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
            assertEquals(workerEntity.company, myWorkerInfo.company)
            assertEquals(workerEntity.location, myWorkerInfo.location)
            assertEquals(workerEntity.introduction, workerEntity.introduction)
            assertEquals(workerEntity.devYear, workerEntity.devYear)
            assertEquals(workerEntity.giveLink, workerEntity.giveLink)
        })
    }

    @Test
    internal fun `worker update test`() {
        // given
        val workerEntity = workerEntityDeepCopy(createWorkerEntity())
        val updateDto = WorkerDto.Update(
            company = RandomString.make(10),
            location = RandomString.make(10),
            introduction = RandomString.make(10),
            giveLink = RandomString.make(10),
            devYear = Random.nextInt(0, 30),
            updateColumns = listOf( // 변경할 컬럼
                WorkerDto.Update.Column.COMPANY,
                WorkerDto.Update.Column.LOCATION,
                WorkerDto.Update.Column.INTRODUCTION,
                WorkerDto.Update.Column.GIVE_LINK,
                WorkerDto.Update.Column.DEV_YEAR
            )
        )
        val authUserInfo = createAuthUserInfoByUserEntity()

        // when
        authUserWorkerService.updateWorkerEntityByAuthUserInfo(authUserInfo, updateDto)

        //then
        val updatedWorkerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
        assertAll("업데이터 전에 조회한 WorkerEntity의 값과 Update후 조회된 WorkerEntity의 값은 달라야 한다.", {
            assertNotEquals(workerEntity.company, updatedWorkerEntity?.company)
            assertNotEquals(workerEntity.location, updatedWorkerEntity?.location)
            assertNotEquals(workerEntity.introduction, updatedWorkerEntity?.introduction)
            assertNotEquals(workerEntity.giveLink, updatedWorkerEntity?.giveLink)
            assertNotEquals(workerEntity.devYear, updatedWorkerEntity?.devYear)
        })

        assertAll("UpdateDto에 저장된, 값들은 update후 조회된 Entity에 반영되어야 한다.", {
            assertEquals(updateDto.company, updatedWorkerEntity?.company)
            assertEquals(updateDto.location, updatedWorkerEntity?.location)
            assertEquals(updateDto.introduction, updatedWorkerEntity?.introduction)
            assertEquals(updateDto.giveLink, updatedWorkerEntity?.giveLink)
            assertEquals(updateDto.devYear, updatedWorkerEntity?.devYear)
        })
    }

    private fun createWorkerEntity() = workerRepository.save(
        WorkerEntity(
            company = RandomString.make(5),
            location = RandomString.make(15),
            introduction = RandomString.make(15),
            giveLink = RandomString.make(15),
            devYear = Random.nextInt(),
            user = this.userEntity
        )
    )

    private fun createAuthUserInfoByUserEntity() = AuthUserInfo(
        githubId = this.userEntity.githubId,
        name = this.userEntity.name,
        email = this.userEntity.email,
        profileImgUri = this.userEntity.profileImgUri,
        role = this.userEntity.role
    )

    private fun workerEntityDeepCopy(workerEntity: WorkerEntity): WorkerEntity = WorkerEntity(
        company = workerEntity.company,
        location = workerEntity.location,
        introduction = workerEntity.introduction,
        giveLink = workerEntity.giveLink,
        devYear = workerEntity.devYear,
        user = userEntity
    )
}
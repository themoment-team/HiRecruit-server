package site.hirecruit.hr.domain.auth.repository

import mu.KotlinLogging
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.entity.UserEntity
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import site.hirecruit.hr.global.config.QuerydslConfig
import kotlin.random.Random

val log = KotlinLogging.logger{}

@LocalTest
@DataJpaTest
@Import(QuerydslConfig::class)
internal class UserCustomRepositoryImplTest{

    @Autowired lateinit var userRepository: UserRepository

    @Autowired lateinit var workerRepository: WorkerRepository

    fun createUserEntity(): UserEntity{
        return userRepository.save(
            UserEntity(
                githubId = Random.nextLong(),
                email = "${RandomString.make(7)}@${RandomString.make(5)}.${RandomString.make(3)}",
                name = RandomString.make(3),
                profileImgUri = RandomString.make(),
                Role.CLIENT
            )
        )
    }

    @Test @DisplayName("findUserAndWorkerEmailByGithubId 테스트")
    fun findUserAndWorkerEntityByGithubIdTest(){
        // given
        val userEntity = createUserEntity()

        // when
        val authUserInfo = userRepository.findUserAndWorkerEmailByGithubId(userEntity.githubId)!!
        log.info("result='$authUserInfo'")

        // then
        assertAll(
            Executable { assertEquals(userEntity.githubId, authUserInfo.githubId) },
            Executable { assertEquals(userEntity.email, authUserInfo.email) },
            Executable { assertEquals(userEntity.name, authUserInfo.name) },
            Executable { assertEquals(userEntity.profileImgUri, authUserInfo.profileImgUri) },
            Executable { assertEquals(userEntity.role, authUserInfo.role) }
        )
    }
}
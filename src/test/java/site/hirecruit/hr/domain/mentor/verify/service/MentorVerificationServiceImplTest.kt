package site.hirecruit.hr.domain.mentor.verify.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import site.hirecruit.hr.global.util.randomNumberGenerator

@LocalTest
@SpringBootTest
@Transactional
internal class MentorVerificationServiceImplTest{

    private val GITHUB_ID = 67095821L

    @BeforeEach
    internal fun setUp(
        @Autowired userRepository: UserRepository,
        @Autowired workerRepository: WorkerRepository,
        @Autowired companyRepository: CompanyRepository
    ) {
        // 더미데이터 생성, postConstruct 메소드 자동 실행
        site.hirecruit.hr.global.dummy.SetDummyData(userRepository, workerRepository, companyRepository)

        // 데미데이터가 잘 생성되었는지 검증
        val expectedImchang = workerRepository.findByUser_GithubId(GITHUB_ID)
        assertThat(expectedImchang?.user?.name).isEqualTo("전지환")
    }

    @Test
    @Disabled
    @DisplayName("실제 SES 서비스를 사용하여 인증번호를 전송합니다. :: Disabled")
    fun verificationCodeWasSentToDestination(
        @Autowired workerRepository: WorkerRepository,
        @Autowired mentorVerificationService: MentorVerificationService
    ){
        val worker = `SES에서 승인된 이메일을 갖고 있는 worker 가져오기 `(workerRepository)

        assertDoesNotThrow {
            mentorVerificationService.sendVerificationCode(worker.workerId!!, worker.user.email, worker.user.name)
        }
    }

    @Test
    @Disabled
    @DisplayName("사용자가 입력한 인증번호 == HR이 발급한 인증번호")
    fun isVerificationCodeCorrectTest(
        @Autowired workerRepository: WorkerRepository,
        @Autowired mentorVerificationService: MentorVerificationService
    ){
        val worker = `SES에서 승인된 이메일을 갖고 있는 worker 가져오기 `(workerRepository)

        val expectedVerificationCode = randomNumberGenerator(6)
        val sendVerificationCode = mentorVerificationService.sendVerificationCode(worker.workerId!!, worker.user.email, worker.user.name)

        assertThrows<IllegalArgumentException> {
            if (sendVerificationCode != expectedVerificationCode) {
                mentorVerificationService.verifyVerificationCode(worker.workerId!!, expectedVerificationCode)
            }
        }

        assertDoesNotThrow {
            mentorVerificationService.verifyVerificationCode(worker.workerId!!, sendVerificationCode)
        }
    }


    private fun `SES에서 승인된 이메일을 갖고 있는 worker 가져오기 `(workerRepository: WorkerRepository): WorkerEntity {
        return workerRepository.findByUser_GithubId(GITHUB_ID)
            ?: throw Exception("email 보낼 대상이 없기 때문에 비즈니스 로직 수행 불가")
    }
}
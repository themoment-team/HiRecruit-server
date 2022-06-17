package site.hirecruit.hr.domain.mentor.verify.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

@LocalTest
@SpringBootTest
@Transactional
internal class MentorVerificationServiceImplTest{

    @BeforeEach
    internal fun setUp(
        @Autowired userRepository: UserRepository,
        @Autowired workerRepository: WorkerRepository,
        @Autowired companyRepository: CompanyRepository
    ) {
        // 더미데이터 생성, postConstruct 메소드 자동 실행
        site.hirecruit.hr.global.dummy.SetDummyData(userRepository, workerRepository, companyRepository)

        // 데미데이터가 잘 생성되었는지 검증
        val expectedImchang = workerRepository.findByUser_Email("hirecruit@gsm.hs.kr")
        assertThat(expectedImchang?.user?.name).isEqualTo("임창규")
    }

    @Test
    @DisplayName("실제 서비스를 사용하여 인증번호를 전송합니다.")
    fun verificationCodeWasSentToDestination(
        @Autowired workerRepository: WorkerRepository,
        @Autowired mentorVerificationService: MentorVerificationService
    ){
        val worker = workerRepository.findByUser_Email("hirecruit@gsm.hs.kr")

        assertDoesNotThrow {
            mentorVerificationService.sendVerificationCode(worker?.workerId, worker?.user?.email, worker?.user?.name)
        }
    }
}
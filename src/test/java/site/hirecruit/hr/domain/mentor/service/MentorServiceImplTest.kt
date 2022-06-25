package site.hirecruit.hr.domain.mentor.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.mentor.verify.repository.MentorEmailVerificationCodeRepository
import site.hirecruit.hr.domain.mentor.verify.service.MentorVerificationService
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

@LocalTest
@SpringBootTest
@Transactional
internal class MentorServiceImplTest{

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

    @Test @Disabled
    @DisplayName("worker 에게 mentor 승격절차를 정상적으로 진행할 수 있다.")
    fun workerToMentorPromotionSuccessfullyWorking(
        @Autowired workerRepository: WorkerRepository,
        @Autowired mentorServiceImpl: MentorService,
        @Autowired mentorVerificationRepository: MentorEmailVerificationCodeRepository
    ){
        // Given :: process 를 진행할 worker 찾기
        val worker = workerRepository.findByUser_GithubId(GITHUB_ID) ?: throw Exception("email에 해당하는 worker 없음")

        // Given :: 로그인
        val authUserInfoCopyWorker = AuthUserInfo(
            worker.user.githubId,
            worker.user.githubLoginId,
            worker.user.name,
            worker.user.email,
            worker.user.profileImgUri,
            worker.user.role
        )

        // When :: process 진행하기
        for(i in 1..10){
            mentorServiceImpl.mentorPromotionProcess(
                githubId = worker.user.githubId,
            )
        }

        // Then :: DB 에서 조회한 값과 == process 가 반환한 값
//        val verificationCode = mentorVerificationRepository.findByIdOrNull(id = worker.workerId!!)
//        assertThat(processing[worker.workerId]).isEqualTo(verificationCode?.verificationCode)
    }

    @Test @Disabled
    @DisplayName("worker가 입력한 인증번호를 원본과 대조하여 역할을 mentor로 업데이트 한다.")
    fun updateWorkerToMentorIfAllStepsPassed(
        @Autowired workerRepository: WorkerRepository,
        @Autowired mentorVerificationServiceImpl: MentorVerificationService,
        @Autowired mentorServiceImpl: MentorService
    ){
        // Given :: setup sandbox access worker
        val worker = workerRepository.findByUser_GithubId(GITHUB_ID) ?: throw Exception("email에 해당하는 worker 없음")

        // Given :: send VerificationCode to worker
        val sentVerificationCode = mentorVerificationServiceImpl.sendVerificationCode(
            workerId = worker.workerId!!,
            workerEmail = worker.user.email,
            workerName = worker.user.name
        )

        // when :: grant 요청 올바른 인증번호로
        assertDoesNotThrow {
            mentorServiceImpl.grantMentorRole(
                githubId = GITHUB_ID,
                verificationCode = sentVerificationCode
            )
        }

        // Then :: 사용자가 입력한 verificationCode가 유효하기 때문에 mentor가 되었을 것.
        assertThat(worker.user.role).isEqualTo(Role.MENTOR)
    }
}
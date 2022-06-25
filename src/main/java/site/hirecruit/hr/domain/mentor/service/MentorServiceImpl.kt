package site.hirecruit.hr.domain.mentor.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.mentor.verify.service.MentorVerificationService
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * @author 전지환
 * @since 1.2.0
 */
@Service
class MentorServiceImpl(
    private val workerRepository: WorkerRepository,
    private val mentorVerificationServiceImpl: MentorVerificationService
) : MentorService {

    /**
     * worker -> mentor 로의 승격절차를 밟는다.
     * 연락수단에 대해 인증을 요청한다.
     *
     * @param githubId mentor로 승격하고 싶은 githubId
     * @return workerId : verificationCode
     */
    override suspend fun mentorPromotionProcess(githubId: Long): Map<Long, String> {
        // githubId로 worker 조회하기
        val workerEntity = findWorkerEntityByGithubIdOrElseThrow(githubId)

        // worker 인증체계에 verificationCode 전송
        val sentVerificationCode = mentorVerificationServiceImpl.sendVerificationCode(
            workerEntity.workerId!!,
            workerEntity.user.email,
            workerEntity.user.name
        )

        return mapOf(workerEntity.workerId!! to sentVerificationCode)
    }

    /**
     * HR의 적합한 mentor 승격 절차를 밟아 역할을 worker -> mentor 업데이트한다.
     * 연락체계에 대한 인증이 완료되어야 한다.
     *
     * @param githubId 사용자 githubId
     * @param verificationCode 사용자가 입력한 인증번호
     * @return workerId - mentor로 승격된 workerId
     */
    @Transactional
    override fun grantMentorRole(githubId: Long, verificationCode: String): Long {
        // githubId로 worker 조회하기
        val workerEntity = findWorkerEntityByGithubIdOrElseThrow(githubId)

        // 인증번호 검증
        mentorVerificationServiceImpl.verifyVerificationCode(workerEntity.workerId!!, verificationCode)
        workerEntity.user.updateRole(Role.MENTOR)

        return workerEntity.workerId!!
    }

    /**
     * githubId를 통해 workerEntity를 조회합니다.
     *
     * @param githubId 찾고자 하는 사용자 githubId
     * @throws Exception worker가 존재하지 않을 때.
     * @return WorkerEntity
     */
    private fun findWorkerEntityByGithubIdOrElseThrow(githubId: Long): WorkerEntity {
        return (workerRepository.findByUser_GithubId(githubId)
            ?: throw IllegalArgumentException("workerId: $githubId 에 해당하는 worker를 찾을 수 없음"))
    }
}
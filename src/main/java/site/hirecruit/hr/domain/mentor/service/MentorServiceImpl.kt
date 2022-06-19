package site.hirecruit.hr.domain.mentor.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.mentor.verify.service.MentorVerificationService
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

@Service
class MentorServiceImpl(
    private val workerRepository: WorkerRepository,
    private val mentorVerificationServiceImpl: MentorVerificationService
) : MentorService {

    /**
     * worker -> mentor 로의 승격절차를 밟는다.
     * 연락수단에 대해 인증을 요청한다.
     */
    override fun mentorPromotionProcess(workerId: Long): Map<Long, String> {
        // 현재 worker가 맞는지 verify
        val workerEntity = (workerRepository.findByIdOrNull(workerId)
            ?: throw Exception("workerId: $workerId 에 해당하는 worker를 찾을 수 없음"))

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
     */
    override fun grantMentorRole(workerId: Long): String {
        
        return "a"
    }
}
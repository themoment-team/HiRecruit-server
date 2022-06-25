package site.hirecruit.hr.domain.mentor.verify.service

import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.mailer.verifyEmail.component.VerificationCodeEmailTemplate
import site.hirecruit.hr.domain.mailer.verifyEmail.service.VerificationEmailSenderService
import site.hirecruit.hr.domain.mentor.verify.entity.MentorEmailVerificationCodeEntity
import site.hirecruit.hr.domain.mentor.verify.repository.MentorEmailVerificationCodeRepository
import site.hirecruit.hr.global.util.randomNumberGenerator


private val log = KotlinLogging.logger {}

/**
 * HR 멘토 인증에 대한 서비스
 *
 * @author 전지환
 * @since 1.2.0
 */
@Service
class MentorVerificationServiceImpl(
    private val verificationEmailSenderService: VerificationEmailSenderService,
    private val verificationCodeEmailTemplate: VerificationCodeEmailTemplate,
    private val mentorEmailVerificationCodeRepository: MentorEmailVerificationCodeRepository
) : MentorVerificationService{

    /**
     * 멘토 인증을 위해 인증번호를 보내주는 서비스
     *
     * @param workerId 멘토를 신청한 재직자 id
     * @param workerId 멘토를 신청한 재직자 email
     * @return verificationCode 전송한 인증번호
     */
    override suspend fun sendVerificationCode(workerId: Long, workerEmail: String, workerName: String) : String = coroutineScope{
        // 난수 인증코드 생성
        val verificationCode = async {
            log.info { "========= randomNumberGenerator() =========" }
            randomNumberGenerator(6)
        }

        // 사용자에게 받은 정보를 기반으로 적절한 이메일 요청 형식을 만든다.
        val mentorEmailVerificationEmailRequest = async {
            log.info { "========= await verificationCode, createMentorEmailVerificationEmailRequest() =========" }
            verificationCodeEmailTemplate.createMentorEmailVerificationEmailRequest(
                workerName = workerName,
                verificationCode = verificationCode.await(),
                workerEmail = workerEmail
            )
        }

        // 인증코드 보내기 v1.2.1 async
        launch {
            log.info { "========= launch sendEmailVerificationSES() =========" }
            verificationEmailSenderService.sendEmailVerificationSES(mentorEmailVerificationEmailRequest.await())
        }


        // [workerId : 인증번호] 저장
        val mentorEmailVerificationCodeEntity = async {
            log.info { "========= make MentorEmailVerificationCodeEntity() =========" }
            MentorEmailVerificationCodeEntity(
                workerId = workerId,
                verificationCode = verificationCode.await()
            )
        }

        launch {
            log.info { "========= await MentorEmailVerificationCodeEntity() and save =========" }
            withContext(Dispatchers.IO) {
                mentorEmailVerificationCodeRepository.save(mentorEmailVerificationCodeEntity.await())
            }
        }

        return@coroutineScope verificationCode.await()
    }

    /**
     * 재직자가 입력한 인증번호가 == 발급된 인증번호 인지 검증하는 서비스
     *
     * @param workerId 재직자 id
     * @param expectedVerificationCode 사용자가 입력한 인증번호
     */
    override fun verifyVerificationCode(workerId: Long, expectedVerificationCode: String) {
        val mentorEmailVerificationCodeEntity = getVerificationCodeByWorkerId(workerId)

        // verify
        val actualVerificationCode = mentorEmailVerificationCodeEntity.verificationCode
        if (actualVerificationCode != expectedVerificationCode){
            throw IllegalArgumentException("사용자가 입력한 verificationCode: $expectedVerificationCode 는 실제 인증번호와 일치하지 않음")
        }
    }

    /**
     * workerId를 통해 verificationCode 를 가져오는 함수
     *
     * @param workerId 재직자 id
     * @return MentorEmailVerificationCodeEntity
     */
    private fun getVerificationCodeByWorkerId(workerId: Long): MentorEmailVerificationCodeEntity {

        return mentorEmailVerificationCodeRepository.findByIdOrNull(workerId)
            ?: throw IllegalArgumentException("workerId: $workerId 로 인증번호를 발급한 기록이 없음.")
    }
}
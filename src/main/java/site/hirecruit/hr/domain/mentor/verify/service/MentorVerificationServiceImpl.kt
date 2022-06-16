package site.hirecruit.hr.domain.mentor.verify.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.emailTemplate.verifyEmail.VerifyEmailServiceImpl
import site.hirecruit.hr.domain.mentor.verify.MentorEmailVerificationCodeEntity
import site.hirecruit.hr.domain.mentor.verify.repository.MentorEmailVerificationCodeRepository
import site.hirecruit.hr.global.util.randomNumberGenerator
import site.hirecruit.hr.thirdParty.aws.ses.dto.SesRequestDto


private val log = KotlinLogging.logger {}

@Service
class MentorVerificationServiceImpl(
    private val verifyEmailServiceImpl: VerifyEmailServiceImpl,
    private val mentorEmailVerificationCodeRepository: MentorEmailVerificationCodeRepository
) : MentorVerificationService{

    /**
     * 멘토 인증을 위해 인증번호를 보내주는 서비스
     *
     * @param workerId 멘토를 신청한 재직자 id
     * @param workerId 멘토를 신청한 재직자 email
     * @return workerEmail 전송에 성공한 toAddress
     */
    override fun sendVerificationCode(workerId: Long, workerEmail: String, workerName: String) : String {
        // 난수 인증코드 생성
        val verificationCode = randomNumberGenerator(6)
        val mentorEmailVerificationCodeEntity = MentorEmailVerificationCodeEntity(workerId, verificationCode)

        // templateSesReqeustDto 작성
        val mentorEmailVerificationEmailRequest = SesRequestDto.TemplateSesRequestDto(
            "HiRecruitEmailAuthenticationTemplate",
            "{ \"name\":\"${workerName}\", \"authenticationCode\":\"${verificationCode}\" }",
            SesRequestDto.DestinationDto(null, null, listOf(workerEmail))
        )

        // 인증코드 보내기 v1.2.1 async
        verifyEmailServiceImpl.sendEmailVerificationSES(mentorEmailVerificationEmailRequest)
        log.info { "======== 인증번호 sendEmail success =======" }

        // workerId : 인증번호 저장 시도
        mentorEmailVerificationCodeRepository.save(mentorEmailVerificationCodeEntity)
        log.info { "=========== 인증번호 redis 저장 완료 ============" }

        return workerEmail
    }

    /**
     * 재직자가 입력한 인증번호가 == 발급된 인증번호 인지 검증하는 서비스
     *
     * @param workerId 재직자 id
     * @param verificationCode 인증번호
     */
    override fun verifyVerificationCode(workerId: Long, verificationCode: String) {

    }
}
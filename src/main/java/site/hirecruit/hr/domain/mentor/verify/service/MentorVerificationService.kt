package site.hirecruit.hr.domain.mentor.verify.service

interface MentorVerificationService {
    fun sendVerificationCode(workerId: Long, workerEmail: String, workerName: String): String
    fun verifyVerificationCode(workerId: Long, verificationCode: String)
}
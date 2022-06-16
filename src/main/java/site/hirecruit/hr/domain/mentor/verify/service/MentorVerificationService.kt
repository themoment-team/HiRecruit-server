package site.hirecruit.hr.domain.mentor.verify.service

interface MentorVerificationService {
    fun verifyVerificationCode(workerId: Long, verificationCode: String)
    fun sendVerificationCode(workerId: Long, workerEmail: String, workerName: String): String
}
package site.hirecruit.hr.domain.mentor.verify.service

interface MentorVerificationService {
    suspend fun sendVerificationCode(workerId: Long, workerEmail: String, workerName: String): String
    fun verifyVerificationCode(workerId: Long, verificationCode: String)
}
package site.hirecruit.hr.domain.emailTemplate.verifyEmail.service

interface VerifyEmailService {
    fun verifyEmail(targetEmail: String) : String
}
package site.hirecruit.hr.domain.mentor.service

interface MentorService {
    fun mentorPromotionProcess(githubId: Long) : Map<Long, String>
    fun grantMentorRole(githubId: Long, verificationCode: String) : Long
}
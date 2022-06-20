package site.hirecruit.hr.domain.mentor.service

interface MentorService {
    fun mentorPromotionProcess(workerId: Long) : Map<Long, String>
    fun grantMentorRole(workerId: Long, verificationCode: String) : Long
}
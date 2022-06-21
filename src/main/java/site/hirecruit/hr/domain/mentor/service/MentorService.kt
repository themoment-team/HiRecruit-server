package site.hirecruit.hr.domain.mentor.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

interface MentorService {
    fun mentorPromotionProcess(workerId: Long, authUserInfo: AuthUserInfo) : Map<Long, String>
    fun grantMentorRole(workerId: Long, verificationCode: String) : Long
}
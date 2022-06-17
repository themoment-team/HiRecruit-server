package site.hirecruit.hr.domain.mentor.verify.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.mentor.verify.entity.MentorEmailVerificationCodeEntity

@Repository
interface MentorEmailVerificationCodeRepository : CrudRepository<MentorEmailVerificationCodeEntity, Long>
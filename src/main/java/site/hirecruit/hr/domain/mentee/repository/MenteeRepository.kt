package site.hirecruit.hr.domain.mentee.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.mentee.entity.MenteeEntity

@Repository
interface MenteeRepository : JpaRepository<MenteeEntity, Long>{
    fun existsByEmail(email: String) : Boolean?
    fun findByMenteeUUID(uuid: String) : MenteeEntity?
}
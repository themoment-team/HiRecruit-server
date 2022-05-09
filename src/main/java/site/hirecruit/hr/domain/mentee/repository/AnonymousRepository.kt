package site.hirecruit.hr.domain.mentee.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.mentee.entity.MenteeEntity

@Repository
interface AnonymousRepository : JpaRepository<MenteeEntity, Long>{
}
package site.hirecruit.hr.domain.mentee.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.hirecruit.hr.domain.mentee.entity.MenteeEntity

interface MenteeRepository : JpaRepository<MenteeEntity, Long>{
}
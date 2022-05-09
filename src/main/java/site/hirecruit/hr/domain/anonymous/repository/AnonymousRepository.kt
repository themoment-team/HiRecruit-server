package site.hirecruit.hr.domain.anonymous.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.anonymous.entity.AnonymousEntity

@Repository
interface AnonymousRepository : JpaRepository<AnonymousEntity, Long>{
}
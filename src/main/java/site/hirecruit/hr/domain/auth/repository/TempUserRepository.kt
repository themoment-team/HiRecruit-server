package site.hirecruit.hr.domain.auth.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.auth.entity.TempUserEntity

@Repository
interface TempUserRepository : CrudRepository<TempUserEntity, Long>
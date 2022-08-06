package site.hirecruit.hr.domain.user.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.user.entity.TempUserEntity

@Repository
interface TempUserRepository : CrudRepository<TempUserEntity, Long>
package site.hirecruit.hr.domain.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.hirecruit.hr.domain.auth.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun existsByGithubId(githubId: Long) : Boolean
}

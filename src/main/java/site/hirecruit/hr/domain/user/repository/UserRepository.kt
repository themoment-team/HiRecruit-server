package site.hirecruit.hr.domain.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.hirecruit.hr.domain.user.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, Long>, UserCustomRepository {
    fun existsByGithubId(githubId: Long) : Boolean

    fun findByGithubId(githubId: Long): UserEntity?

    fun deleteByGithubId(githubId: Long)
}

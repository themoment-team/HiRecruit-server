package site.hirecruit.hr.domain.worker.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

@Repository
interface WorkerRepository : JpaRepository<WorkerEntity, Long> {

    fun existsByGithubId(id: Long): Boolean

    fun findByGithubId(githubId: Long): WorkerEntity?
}
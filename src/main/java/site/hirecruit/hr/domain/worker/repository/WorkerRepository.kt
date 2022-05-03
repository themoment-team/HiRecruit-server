package site.hirecruit.hr.domain.worker.repository

import org.springframework.data.jpa.repository.JpaRepository
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

interface WorkerRepository : JpaRepository<WorkerEntity, Long> {

    fun existsByGithubId(id: Long): Boolean

    fun findByGithubId(githubId: Long): WorkerEntity?
}
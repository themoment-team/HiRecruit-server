package site.hirecruit.hr.domain.worker.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

@Repository
interface WorkerRepository : JpaRepository<WorkerEntity, Long>, WorkerCustomRepository {

    fun findByUser_GithubId(githubId: Long): WorkerEntity?

    fun findByCompany_CompanyId(companyId: Long): List<WorkerEntity>
}
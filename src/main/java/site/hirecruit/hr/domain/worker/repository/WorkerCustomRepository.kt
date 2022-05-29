package site.hirecruit.hr.domain.worker.repository

import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * WorkerCustomRepository
 *
 * @author 정시원
 * @since 1.0
 */
interface WorkerCustomRepository {

    fun findWorkerInfoDtoByWorkerId(workerId: Long): WorkerDto.Info

    fun findWorkerInfoDtoByCompanyId(companyId: Long): List<WorkerDto.Info>

    fun findWorkerInfoDtoByAll(): List<WorkerDto.Info>
}
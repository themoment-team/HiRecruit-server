package site.hirecruit.hr.domain.worker.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * WorkerLockupService 구현체
 *
 * @author 정시원
 * @since 1.0
 */
@Service
class WorkerLockupServiceImpl(
    private val workerRepository: WorkerRepository
): WorkerLockupService {


    override fun findByWorkerId(workerId: Long): WorkerDto.Info =
        workerRepository.findWorkerInfoDtoByWorkerId(workerId)
            ?: throw IllegalArgumentException("No corresponding worker was found. workerId='$workerId'")

    override fun findByCompanyId(companyId: Long): List<WorkerDto.Info> =
        workerRepository.findWorkerInfoDtoByCompanyId(companyId)

    override fun findAll(): List<WorkerDto.Info> =
        workerRepository.findAllWorkerInfoDto()
            .shuffled()
}
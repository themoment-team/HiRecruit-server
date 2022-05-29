package site.hirecruit.hr.domain.worker.service

import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * Worker정보를 조회하는 서비스
 *
 * @author 정시원
 * @since 1.0
 */
interface WorkerLockupService {

    fun findByWorkerId(workerId: Long): WorkerDto.Info

    fun findByCompanyId(companyId: Long): WorkerDto.Info

    fun findAll(): WorkerDto.Info
}
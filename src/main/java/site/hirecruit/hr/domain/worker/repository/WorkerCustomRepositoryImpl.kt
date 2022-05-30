package site.hirecruit.hr.domain.worker.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import site.hirecruit.hr.domain.worker.dto.WorkerDto

/**
 * WorkerCustomRepository
 *
 * @author 정시원
 * @since 1.0
 */
class WorkerCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): WorkerCustomRepository {

    override fun findWorkerInfoDtoByWorkerId(workerId: Long): WorkerDto.Info? {
        TODO("Not yet implemented")
    }

    override fun findWorkerInfoDtoByCompanyId(companyId: Long): List<WorkerDto.Info> {
        TODO("Not yet implemented")
    }

    override fun findWorkerInfoDtoByAll(): List<WorkerDto.Info> {
        TODO("Not yet implemented")
    }

}
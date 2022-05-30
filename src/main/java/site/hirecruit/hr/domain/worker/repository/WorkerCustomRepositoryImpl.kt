package site.hirecruit.hr.domain.worker.repository

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import site.hirecruit.hr.domain.auth.entity.QUserEntity.userEntity
import site.hirecruit.hr.domain.company.dto.QCompanyDto_Info
import site.hirecruit.hr.domain.company.entity.QCompanyEntity.companyEntity
import site.hirecruit.hr.domain.worker.dto.QWorkerDto_Info
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.QWorkerEntity.workerEntity

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
        return queryFactory
            .select(QWorkerDto_Info(
                Expressions.constantAs(workerId, workerEntity.workerId),
                workerEntity.user.name,
                workerEntity.user.email,
                workerEntity.user.profileImgUri,
                workerEntity.introduction,
                workerEntity.giveLink,
                workerEntity.devYear,
                workerEntity.position,
                QCompanyDto_Info(
                        workerEntity.company.companyId,
                        workerEntity.company.name,
                        workerEntity.company.location,
                        workerEntity.company.homepageUri,
                        workerEntity.company.imageUri
                    )
                )
            ).from(workerEntity)
            .join(workerEntity.user, userEntity)
            .join(workerEntity.company, companyEntity)
            .where(workerEntity.workerId.eq(workerId))
            .fetchOne()
    }

    override fun findWorkerInfoDtoByCompanyId(companyId: Long): List<WorkerDto.Info> {
        return queryFactory
            .select(QWorkerDto_Info(
                    workerEntity.workerId,
                    workerEntity.user.name,
                    workerEntity.user.email,
                    workerEntity.user.profileImgUri,
                    workerEntity.introduction,
                    workerEntity.giveLink,
                    workerEntity.devYear,
                    workerEntity.position,
                    QCompanyDto_Info(
                        Expressions.constantAs(companyId, workerEntity.company.companyId),
                        workerEntity.company.name,
                        workerEntity.company.location,
                        workerEntity.company.homepageUri,
                        workerEntity.company.imageUri
                    )
                )
            )
            .from(workerEntity)
            .join(workerEntity.user, userEntity)
            .join(workerEntity.company, companyEntity)
            .where(companyEntity.companyId.eq(companyId))
            .fetch()
    }

    override fun findAllWorkerInfoDto(): List<WorkerDto.Info> {
        return queryFactory
            .select(QWorkerDto_Info(
                    workerEntity.workerId,
                    workerEntity.user.name,
                    workerEntity.user.email,
                    workerEntity.user.profileImgUri,
                    workerEntity.introduction,
                    workerEntity.giveLink,
                    workerEntity.devYear,
                    workerEntity.position,
                    QCompanyDto_Info(
                        workerEntity.company.companyId,
                        workerEntity.company.name,
                        workerEntity.company.location,
                        workerEntity.company.homepageUri,
                        workerEntity.company.imageUri
                    )
                )
            )
            .from(workerEntity)
            .join(workerEntity.user, userEntity)
            .join(workerEntity.company, companyEntity)
            .fetch()
    }

}
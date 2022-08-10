package site.hirecruit.hr.domain.worker.service

import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.mapper.WorkerEntityMapper
import site.hirecruit.hr.domain.worker.mapper.WorkerInfoDtoMapper
import site.hirecruit.hr.domain.worker.repository.WorkerRepository
import site.hirecruit.hr.global.event.WorkerRegistrationEvent

val log = KotlinLogging.logger {}

/**
 * Worker 등록 Service implement
 *
 * @author 정시원
 * @since 1.0
 */
@Service
class WorkerRegistrationServiceImpl(
    private val userRepository: UserRepository,
    private val workerRepository: WorkerRepository,
    private val companyRepository: CompanyRepository,
    private val publisher: ApplicationEventPublisher
): WorkerRegistrationService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info{
        publisher.publishEvent(WorkerRegistrationEvent(authUserInfo, registrationDto))
        val userEntity = userRepository.findByGithubId(authUserInfo.githubId)
            ?: throw IllegalStateException("Cannot found UserEntity, githubId='${authUserInfo.githubId}'")
        val companyEntity = companyRepository.findByIdOrNull(registrationDto.companyId)
            ?: throw IllegalArgumentException("Cannot found CompanyEntity, companyId='${registrationDto.companyId}'")

        val savedWorkerEntity = workerRepository.save(
            WorkerEntityMapper.INSTANCE.toEntity(registrationDto, userEntity, companyEntity)
        )
        return WorkerInfoDtoMapper.INSTANCE.toWorkerInfoDto(savedWorkerEntity, authUserInfo)
    }

}
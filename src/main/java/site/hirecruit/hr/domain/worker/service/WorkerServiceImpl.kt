package site.hirecruit.hr.domain.worker.service

import org.springframework.data.repository.findByIdOrNull
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * WorkerService
 *
 * @author 정시원
 */
class WorkerServiceImpl(
    private val workerRepository: WorkerRepository,
    private val userRepository: UserRepository
) : WorkerService {

    override fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info {
        val userEntity = userRepository.findByIdOrNull(registrationDto.userId)
            ?: throw IllegalStateException("Cannot found UserEntity, userId='${registrationDto.userId}'")
        val savedWorkerEntity = workerRepository.save(
            WorkerEntity(
                company = registrationDto.company,
                location = registrationDto.location,
                introduction = registrationDto.introduction,
                giveLink = registrationDto.giveLink,
                devYear = registrationDto.devYear,
                userEntity
            )
        )
        return WorkerDto.Info(
            authUserInfo = authUserInfo,
            company = savedWorkerEntity.company,
            location = savedWorkerEntity.location,
            introduction = savedWorkerEntity.introduction,
            devYear = savedWorkerEntity.devYear
        )
    }

    override fun findWorkerByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info {
        TODO("Not yet implemented")
    }

    override fun update(updateDto: WorkerDto.Update) {
        TODO("Not yet implemented")
    }
}
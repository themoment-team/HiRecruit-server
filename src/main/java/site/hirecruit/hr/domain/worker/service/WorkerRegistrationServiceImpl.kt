package site.hirecruit.hr.domain.worker.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * Worker 등록 Service implement
 *
 * @author 정시원
 * @since 1.0
 */
class WorkerRegistrationServiceImpl(
    private val userRepository: UserRepository,
    private val workerRepository: WorkerRepository
): WorkerRegistrationService {

    override fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info{
        val userEntity = userRepository.findByGithubId(authUserInfo.githubId)
            ?: throw IllegalStateException("Cannot found UserEntity, githubId='${authUserInfo.githubId}'")
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
            name = authUserInfo.name,
            email = authUserInfo.email!!,
            profileImgUri = authUserInfo.profileImgUri,
            company = savedWorkerEntity.company,
            location = savedWorkerEntity.location,
            introduction = savedWorkerEntity.introduction,
            giveLink = savedWorkerEntity.giveLink,
            devYear = savedWorkerEntity.devYear
        )
    }

}
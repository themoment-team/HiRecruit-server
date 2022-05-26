package site.hirecruit.hr.domain.worker.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * WorkerService Implement
 *
 * @author 정시원
 */
@Service
class WorkerServiceImpl(
    private val workerRepository: WorkerRepository,
    private val userRepository: UserRepository
) : WorkerService {

    override fun registration(authUserInfo: AuthUserInfo, registrationDto: WorkerDto.Registration): WorkerDto.Info {
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

    override fun findWorkerByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info {
        val workerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Invalid authentication information so cannot found 'WorkerEntity'. authUserInfo = '${authUserInfo}' ")
        return WorkerDto.Info(
            name = authUserInfo.name,
            email = authUserInfo.email!!,
            profileImgUri = authUserInfo.profileImgUri,
            company = workerEntity.company,
            location = workerEntity.location,
            introduction = workerEntity.introduction,
            giveLink = workerEntity.giveLink,
            devYear =  workerEntity.devYear
        )
    }

    override fun update(authUserInfo: AuthUserInfo, updateDto: WorkerDto.Update) {
        val workerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Invalid authentication information so cannot found 'WorkerEntity'. authUserInfo = '${authUserInfo}' ")

        updateDto.updateColumns.forEach {
            when(it) {
                WorkerDto.Update.Column.COMPANY         -> workerEntity.company = updateDto.company!!
                WorkerDto.Update.Column.LOCATION        -> workerEntity.location = updateDto.location!!
                WorkerDto.Update.Column.INTRODUCTION    -> workerEntity.introduction = updateDto.introduction
                WorkerDto.Update.Column.GIVE_LINK       -> workerEntity.giveLink = updateDto.giveLink
                WorkerDto.Update.Column.DEV_YEAR        -> workerEntity.devYear = updateDto.devYear
            }
        }
    }
}
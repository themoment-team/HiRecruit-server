package site.hirecruit.hr.domain.worker.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * AuthUserWorkerService 인증된 유저의 Worker service
 *
 * @author 정시원
 * @since 1.0
 */
@Service
class AuthUserWorkerServiceImpl(
    private val workerRepository: WorkerRepository,
) : AuthUserWorkerService {

    @Transactional(readOnly = true)
    override fun findWorkerInfoByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info {
        val workerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Invalid authentication information. So 'WorkerEntity' could not be found. authUserInfo = '${authUserInfo}' ")
        return WorkerDto.Info(
            name = authUserInfo.name,
            email = authUserInfo.email!!,
            profileImgUri = authUserInfo.profileImgUri,
            companyName = workerEntity.companyName,
            location = workerEntity.location,
            introduction = workerEntity.introduction,
            giveLink = workerEntity.giveLink,
            devYear =  workerEntity.devYear
        )
    }

    @Transactional
    override fun updateWorkerEntityByAuthUserInfo(authUserInfo: AuthUserInfo, updateDto: WorkerDto.Update) {
        val workerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Invalid authentication information. So 'WorkerEntity' could not be found. authUserInfo = '${authUserInfo}' ")
        updateDto.updateColumns.forEach {
            when(it) {
                WorkerDto.Update.Column.COMPANY_NAME         -> workerEntity.companyName = updateDto.companyName!!
                WorkerDto.Update.Column.LOCATION        -> workerEntity.location = updateDto.location!!
                WorkerDto.Update.Column.INTRODUCTION    -> workerEntity.introduction = updateDto.introduction
                WorkerDto.Update.Column.GIVE_LINK       -> workerEntity.giveLink = updateDto.giveLink
                WorkerDto.Update.Column.DEV_YEAR        -> workerEntity.devYear = updateDto.devYear
            }
        }
    }
}
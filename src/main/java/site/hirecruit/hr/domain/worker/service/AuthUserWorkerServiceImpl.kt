package site.hirecruit.hr.domain.worker.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.repository.CompanyRepository
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
    private val companyRepository: CompanyRepository
) : AuthUserWorkerService {

    @Transactional(readOnly = true)
    override fun findWorkerInfoByAuthUserInfo(authUserInfo: AuthUserInfo): WorkerDto.Info {
        val workerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Invalid authentication information. So 'WorkerEntity' could not be found. authUserInfo = '${authUserInfo}' ")
        return WorkerDto.Info(
            workerId = workerEntity.workerId!!,
            name = workerEntity.user.name,
            email = workerEntity.user.email,
            githubLoginId = workerEntity.user.githubLoginId,
            profileImgUri = workerEntity.user.profileImgUri,
            introduction = workerEntity.introduction,
            devYear =  workerEntity.devYear,
            position = workerEntity.position,
            userType = workerEntity.user.role,
            companyInfoDto = CompanyDto.Info(
                companyId = workerEntity.company.companyId!!,
                name = workerEntity.company.name,
                location = workerEntity.company.location,
                homepageUri = workerEntity.company.homepageUri,
                companyImgUri = workerEntity.company.companyImgUri
            )
        )
    }

    @Transactional
    override fun updateWorkerEntityByAuthUserInfo(authUserInfo: AuthUserInfo, updateDto: WorkerDto.Update) {
        val workerEntity = workerRepository.findByUser_GithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Invalid authentication information. So 'WorkerEntity' could not be found. authUserInfo = '${authUserInfo}' ")
        val company = (companyRepository.findByIdOrNull(updateDto.companyId)
            ?: throw IllegalArgumentException("Cannot found CompanyEntity, companyId='${updateDto.companyId}'"))

        workerEntity.update(updateDto, company)
    }
}
package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserUpdateDto
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.UserUpdateService

@Service
class UserUpdateServiceImpl(
    private val userRepository: UserRepository
): UserUpdateService {

    @Transactional
    override fun update(updateDto: UserUpdateDto, authUserInfo: AuthUserInfo): AuthUserInfo {
        val userEntity = userRepository.findByGithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Cannot found user info")
        val savedUserEntity = userEntity.update(updateDto)
        return AuthUserInfo(
            githubId = savedUserEntity.githubId,
            githubLoginId = savedUserEntity.githubLoginId,
            name = savedUserEntity.name,
            email = savedUserEntity.email,
            profileImgUri = savedUserEntity.profileImgUri,
            role = savedUserEntity.role
        )
    }


}
package site.hirecruit.hr.domain.user.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.UserUpdateDto
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.user.repository.UserRepository

@Service
class UserUpdateServiceImpl(
    private val userRepository: UserRepository
): UserUpdateService {

    @Transactional
    override fun update(updateDto: UserUpdateDto, authUserInfoBeforeUpdate: AuthUserInfo): AuthUserInfo {
        val userEntity = userRepository.findByGithubId(authUserInfoBeforeUpdate.githubId)
            ?: throw IllegalArgumentException("Cannot found user info")
        val updatedUserEntity = userEntity.update(updateDto)

        if(isMentorEmailChanged(authUserInfoBeforeUpdate, updatedUserEntity))
            updatedUserEntity.updateRole(Role.WORKER)
        return AuthUserInfo(
            githubId = updatedUserEntity.githubId,
            githubLoginId = updatedUserEntity.githubLoginId,
            name = updatedUserEntity.name,
            email = updatedUserEntity.email,
            profileImgUri = updatedUserEntity.profileImgUri,
            role = updatedUserEntity.role
        )
    }

    private fun isMentorEmailChanged(authUserInfoBeforeUpdate: AuthUserInfo, updatedUserEntity: UserEntity): Boolean {
        if(authUserInfoBeforeUpdate.role != Role.MENTOR) // 멘토가 아니라면
            return false

        return authUserInfoBeforeUpdate.email.equals(updatedUserEntity.email).not()
    }


}
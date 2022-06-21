package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserUpdateDto
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.UserUpdateService

@Service
class UserUpdateServiceImpl(
    private val userRepository: UserRepository
): UserUpdateService {

    override fun update(updateDto: UserUpdateDto, authUserInfo: AuthUserInfo) {
        val userEntity = userRepository.findByGithubId(authUserInfo.githubId)
            ?: throw IllegalArgumentException("Cannot found user info")

        updateDto.updateColumns.forEach {
            when(it) {
                UserUpdateDto.Column.EMAIL      -> userEntity.email = updateDto.email!!
                UserUpdateDto.Column.NAME       -> userEntity.name = updateDto.name!!
            }
        }

        userRepository.save(userEntity)
    }


}
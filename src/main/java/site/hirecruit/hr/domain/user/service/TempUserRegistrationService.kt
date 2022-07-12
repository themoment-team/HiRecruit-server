package site.hirecruit.hr.domain.user.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.TempUserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.user.dto.TempUserRegistrationDto
import site.hirecruit.hr.domain.user.entity.Role

/**
 * 임시 유저 등록 서비스
 *
 * @author 정시원
 * @since 1.3
 */
class TempUserRegistrationService(
    private val tempUserRepository: TempUserRepository
): NewUserRegistrationService<TempUserRegistrationDto> {

    /**
     * 임시 사용자 등록 매서드
     *
     * @return 이미 등록되었거나 등록한 임시 사용자 정보
     */
    override fun registration(registrationDto: TempUserRegistrationDto): AuthUserInfo {
        if(tempUserRepository.existsById(registrationDto.githubId))
            return AuthUserInfo(
                githubId = registrationDto.githubId,
                githubLoginId = registrationDto.githubLoginId,
                name = "임시유저",
                email = null,
                profileImgUri = registrationDto.profileImgUri,
                role = Role.GUEST
            )

        val savedTempUserEntity = tempUserRepository.save(
            TempUserEntity(
                githubId = registrationDto.githubId,
                githubLoginId = registrationDto.githubLoginId,
                profileImgUri = registrationDto.profileImgUri
            )
        )
        return AuthUserInfo(
            githubId = savedTempUserEntity.githubId,
            githubLoginId = savedTempUserEntity.githubLoginId,
            name = "임시유저",
            email = null,
            profileImgUri = savedTempUserEntity.profileImgUri,
            role = Role.GUEST
        )
    }
}
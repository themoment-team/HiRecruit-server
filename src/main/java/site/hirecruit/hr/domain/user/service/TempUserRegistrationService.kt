package site.hirecruit.hr.domain.user.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.mapper.AuthUserInfoMapper
import site.hirecruit.hr.domain.user.entity.TempUserEntity
import site.hirecruit.hr.domain.user.repository.TempUserRepository
import site.hirecruit.hr.domain.user.dto.TempUserRegistrationDto
import site.hirecruit.hr.domain.user.mapper.TempUserEntityMapper

/**
 * 임시 유저 등록 서비스
 *
 * @author 정시원
 * @since 1.3
 */
class TempUserRegistrationService(
    private val tempUserRepository: TempUserRepository
): UserRegistrationService<TempUserRegistrationDto> {

    /**
     * 임시 사용자 등록 매서드
     *
     * @return 이미 등록되었거나 등록한 임시 사용자 정보
     */
    override fun registration(registrationDto: TempUserRegistrationDto): AuthUserInfo {
        if(tempUserRepository.existsById(registrationDto.githubId))
            return AuthUserInfoMapper.INSTANCE.toAuthUserInfo(registrationDto)

        val savedTempUserEntity = tempUserRepository.save(
            TempUserEntityMapper.INSTANCE.toEntity(registrationDto)
        )
        return AuthUserInfoMapper.INSTANCE.toAuthUserInfo(savedTempUserEntity)
    }
}
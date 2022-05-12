package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.entity.TempUserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.service.TempUserRegistrationService

/**
 * 임시 유저를 생성하는 service입니다. 임시 유저가 존재하면 존재하는 유저를 return합니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Service
class TempUserRegistrationServiceImpl(
    private val tempUserRepository: TempUserRepository
) : TempUserRegistrationService {
    override fun registration(oAuthAttributes: OAuthAttributes) {
        val tempUserEntity = TempUserEntity(
            githubId = oAuthAttributes.id,
            name = oAuthAttributes.name,
            profileUri = oAuthAttributes.profileImgUri
        )
        if(tempUserRepository.existsById(oAuthAttributes.id).not())
            tempUserRepository.save(tempUserEntity)
    }
}
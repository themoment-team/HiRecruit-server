package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.entity.TempUserEntity
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.service.TempUserRegistrationService

/**
 * 임시 유저를 생성하는 service입니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Service
class TempUserRegistrationServiceImpl(
    private val tempUserRepository: TempUserRepository
) : TempUserRegistrationService {
    override fun registration(oAuthAttributes: OAuthAttributes) {
        if(tempUserRepository.existsById(oAuthAttributes.id))
            return

        val tempUserEntity = TempUserEntity(
            githubId = oAuthAttributes.id,
            profileImgUri = oAuthAttributes.profileImgUri
        )
        tempUserRepository.save(tempUserEntity)
    }
}
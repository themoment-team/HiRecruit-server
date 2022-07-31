package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.user.entity.TempUserEntity
import site.hirecruit.hr.domain.user.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.service.TempUserRegistrationService

/**
 * 임시 유저를 생성하는 service입니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Service
@Deprecated("해당 구현체는 v1.3부터 더 이상 사용하지 않습니다. 해당 구현체의 역할은 domain.user.TempUserService로 이전될 에정입니다.")
class TempUserRegistrationServiceImpl(
    private val tempUserRepository: TempUserRepository
) : TempUserRegistrationService {
    override fun registration(oAuthAttributes: OAuthAttributes) {
        if(tempUserRepository.existsById(oAuthAttributes.id))
            return

        val tempUserEntity = TempUserEntity(
            githubId = oAuthAttributes.id,
            githubLoginId = oAuthAttributes.attributes["login"] as String,
            profileImgUri = oAuthAttributes.profileImgUri
        )
        tempUserRepository.save(tempUserEntity)
    }
}
package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.OAuthProcessorFacade
import site.hirecruit.hr.domain.auth.service.UserAuthService
import site.hirecruit.hr.domain.auth.service.TempUserRegistrationService

/**
 * OAuth2 인증에 관련된 로직을 실행하는 퍼사드 패턴의 구현체입니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Service
class OAuth2ProcessorFacadeImpl(
    private val userRepository: UserRepository,
    private val tempUserRegistrationService: TempUserRegistrationService,
    private val userAuthService: UserAuthService
) : OAuthProcessorFacade {

    override fun process(oauthAttributes: OAuthAttributes): AuthUserInfo {
        if(userRepository.existsByGithubId(oauthAttributes.id).not()) // 첫 사용자라면 임시 계정을 등록한다.
            tempUserRegistrationService.registration(oauthAttributes)

        return userAuthService.authentication(oauthAttributes)
    }

}
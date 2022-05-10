package site.hirecruit.hr.domain.auth.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

/**
 * OAuth2 인증에 관련된 로직을 실행하는 퍼사드 패턴의 구현체입니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Service
class OAuth2ProcessorFacadeImpl(
    private val workerRepository: WorkerRepository,
    private val userRegistrationService: UserRegistrationService,
    private val userAuthService: UserAuthService
) : OAuthProcessorFacade {

    override fun process(oauthAttributes: OAuthAttributes): User {
        // 첫 사용자라면 계정을 등록한다.
        if(workerRepository.existsByGithubId(oauthAttributes.id))
            userRegistrationService.registration(oauthAttributes)

        return userAuthService.authentication(oauthAttributes)
    }

}
package site.hirecruit.hr.domain.auth.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User
import site.hirecruit.hr.domain.worker.repository.WorkerRepository

@Service
class AuthProcessorImpl(
    private val workerRepository: WorkerRepository,
    private val userRegistrationService: UserRegistrationService,
    private val userAuthService: UserAuthService
) : AuthProcessor {

    override fun process(oauthAttributes: OAuthAttributes): User {
        // 첫 사용자라면 계정을 등록한다.
        if(workerRepository.existsByGithubId(oauthAttributes.id))
            userRegistrationService.registration(oauthAttributes)

        return userAuthService.authentication(oauthAttributes)
    }

}
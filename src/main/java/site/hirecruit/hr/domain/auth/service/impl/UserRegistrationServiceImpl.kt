package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.service.UserRegistrationService

/**
 * @author 정시원
 * @version 1.0
 */
@Service
class UserRegistrationServiceImpl : UserRegistrationService {
    override fun registration(oAuthAttributes: OAuthAttributes) {
        TODO("User registration logic not implemented.")
    }
}
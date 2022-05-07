package site.hirecruit.hr.domain.auth.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User

@Service
class UserAuthServiceImpl : UserAuthService {
    override fun authentication(oAuthAttributes: OAuthAttributes): User {
        TODO("User authentication logic not implemented.")
    }
}
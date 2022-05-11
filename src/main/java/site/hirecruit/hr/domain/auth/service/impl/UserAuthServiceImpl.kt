package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User
import site.hirecruit.hr.domain.auth.service.UserAuthService

/**
 * 세션기반 인증을 진행하는 서비스
 *
 * @author 정시원
 * @version 1.0
 */
@Service
class UserAuthServiceImpl : UserAuthService {
    override fun authentication(oAuthAttributes: OAuthAttributes): User {
        TODO("User authentication logic not implemented.")
    }
}
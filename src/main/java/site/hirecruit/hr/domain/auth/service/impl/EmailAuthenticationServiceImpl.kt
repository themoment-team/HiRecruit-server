package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.service.EmailAuthenticationService

@Service
class EmailAuthenticationServiceImpl: EmailAuthenticationService {
    override fun send(authUserInfo: AuthUserInfo, email: String): String {
        TODO("Not yet implemented")
    }

    override fun tokenVerification(email: String): Boolean {
        TODO("Not yet implemented")
    }
}
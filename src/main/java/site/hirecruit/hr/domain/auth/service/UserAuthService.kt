package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User

interface UserAuthService {

    fun authentication(oAuthAttributes: OAuthAttributes): User
}
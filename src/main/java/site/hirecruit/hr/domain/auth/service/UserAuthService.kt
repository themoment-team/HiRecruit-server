package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User

/**
 * 유저의 인증을 담당하는 서비스
 *
 * @author 정시원
 * @version 1.0
 */
interface UserAuthService {

    /**
     * 인증을 진행합니다.
     *
     * @return [User] - 인증된 유저의 정보
     * @throws OAuth2AuthenticationException 인증에 실패할 경우 발생합니다.
     */
    fun authentication(oAuthAttributes: OAuthAttributes): User
}
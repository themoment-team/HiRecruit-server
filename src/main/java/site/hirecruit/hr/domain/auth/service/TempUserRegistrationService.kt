package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes

/**
 * 임시 유저를 생성하는 서비스
 *
 * @author 정시원
 * @version 1.0
 */
@Deprecated("해당 인터페이스는 v1.3부터 더 이상 사용되지 않습니다. domain.user.service 패키지에 TempUserRegistrationService로 대체됩니다.")
interface TempUserRegistrationService {

    /**
     * 임시 유저를 생성합니다.
     */
    fun registration(oAuthAttributes: OAuthAttributes)
}
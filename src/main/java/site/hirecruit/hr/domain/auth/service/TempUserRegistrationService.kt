package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes

/**
 * 임시 유저를 생성하는 서비스
 *
 * @author 정시원
 * @version 1.0
 */
interface TempUserRegistrationService {

    /**
     * 임시 유저를 생성합니다.
     */
    fun registration(oAuthAttributes: OAuthAttributes)
}
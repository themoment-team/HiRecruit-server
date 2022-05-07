package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes

/**
 * 유저의 회원가입을 담당하는 서비스
 *
 * @author 정시원
 * @version 1.0
 */
interface UserRegistrationService {

    /**
     * 유저를 생성합니다.
     */
    fun registration(oAuthAttributes: OAuthAttributes)
}
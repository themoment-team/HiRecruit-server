package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User

/**
 * 유저의 로그인/회원가입을 담당하는 일종의 퍼사드 패턴입니다.
 *
 * @see CustomOAuth2UserService
 * @author 정시원
 * @version 1.0
 */
interface AuthProcessor {

    fun process(oauthAttributes : OAuthAttributes) : User
}
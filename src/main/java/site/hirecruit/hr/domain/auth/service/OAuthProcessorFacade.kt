package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User

/**
 * OAuth 인증에 관련된 로직을 실행하는 퍼사드 패턴입니다.
 *
 * @see CustomOAuth2UserService
 * @author 정시원
 * @version 1.0
 */
interface OAuthProcessorFacade {

    /**
     * 유저의 로그인(인증)/회원가입을 담당합니다.
     *
     * @param [User] 인증에 성공한 User 객체입니다.
     * @throws OAuth2AuthenticationException 인증에 실패할 경우 발생합니다.
     */
    fun process(oauthAttributes : OAuthAttributes) : User
}
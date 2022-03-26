package site.hirecruit.hr.domain.auth.service;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes;
import site.hirecruit.hr.domain.auth.dto.SessionUser;

/**
 * 인증(로그인)를 진행하는 서비스
 *
 * @author 정시원
 * @see UserAuthServiceImpl
 */
public interface UserAuthService {

    /**
     * 세션을 이용한 로그인을 진행한다.
     *
     * @return 세션에 저장된 데이터
     */
    SessionUser login(OAuthAttributes oAuthAttributes) throws OAuth2AuthenticationException;
}

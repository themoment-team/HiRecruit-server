package site.hirecruit.hr.domain.auth.service;

import site.hirecruit.hr.domain.auth.dto.OAuthAttributes;
import site.hirecruit.hr.domain.auth.dto.SessionUser;

public interface UserAuthService {

    /**
     * 세션을 이용한 로그인을 진행한다.
     *
     * @return 세션에 저장된 데이터
     */
    SessionUser login(OAuthAttributes oAuthAttributes);
}

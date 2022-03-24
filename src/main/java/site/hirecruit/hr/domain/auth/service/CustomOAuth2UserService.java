package site.hirecruit.hr.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 실제 OAuth 로직을 작성하는 서비스
 *
 * @version 1.0
 */
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOAuth2UserService;

    @Autowired
    public CustomOAuth2UserService() {
        this.delegateOAuth2UserService = new DefaultOAuth2UserService();
    }

    public CustomOAuth2UserService(
            OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOAuth2UserService
    ) {
        this.delegateOAuth2UserService = delegateOAuth2UserService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }


}

package site.hirecruit.hr.domain.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes;
import site.hirecruit.hr.domain.auth.dto.SessionUser;

import java.util.Collections;

/**
 * 실제 OAuth 로직을 작성하는 서비스 (Github기준으로 작성해서 확장성 1도 없음)
 *
 * @version 1.0
 */
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegateOAuth2UserService;
    private final UserRegistrationService userRegistrationService;
    private final UserAuthService userAuthService;

    @Autowired
    public CustomOAuth2UserService(UserRegistrationService userRegistrationService, UserAuthService userAuthService) {
        this.delegateOAuth2UserService = new DefaultOAuth2UserService();
        this.userRegistrationService = userRegistrationService;
        this.userAuthService = userAuthService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = delegateOAuth2UserService.loadUser(userRequest); // 로그인을 시도한 User의 정보
        final String registrationId = userRequest.getClientRegistration().getRegistrationId(); // OAuth2 벤더
        final String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        log.debug("registrationId = '{}'", registrationId);
        log.debug("userNameAttributeName = '{}'", userNameAttributeName);
        log.debug("oAuth2User.getAttributes = '{}'", Collections.unmodifiableMap(oAuth2User.getAttributes()));

        final OAuthAttributes attributes = OAuthAttributes.of(
                registrationId,
                userNameAttributeName,
                oAuth2User.getAttributes()
        );

        // 만약 서비스를 처음 사용하는 사용자라면
        if(userRegistrationService.isFirst(attributes.getId()))
            userRegistrationService.registration(attributes)
                    .orElseThrow(() -> new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT));

        final SessionUser loginUser = userAuthService.login(attributes); // 로그인

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(loginUser.getRole().getRole())),
                attributes.getAttributes(),
                attributes.getUserNameAttributeName()
        );
    }
}

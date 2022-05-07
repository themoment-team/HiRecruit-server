package site.hirecruit.hr.domain.auth.service

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.model.User
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * OAuth2 로직의 entry service입니다.
 *
 * /oauth2/authorization/github 경로를 통해 들어온 oauth2인증/인가를 [CustomOAuth2UserService.loadUser]에서 처리합니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Service
class CustomOAuth2UserService(
    private val authProcessor: AuthProcessor,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private var delegateOauth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User> = DefaultOAuth2UserService()

    /**
     * OAuth2인증/인가를 진행합니다.
     * @throws OAuth2AuthenticationException OAuth2 인증/인가가 실패할경우 발생합니다.
     */
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User : OAuth2User =  delegateOauth2UserService.loadUser(userRequest)
        val registrationId : String = userRequest.clientRegistration.registrationId
        val userNameAttributeName : String = userRequest.clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName

        log.debug("registrationId = '${registrationId}'");
        log.debug("userNameAttributeName = '${userNameAttributeName}'");
        log.debug("oAuth2User.getAttributes = '${oAuth2User.attributes}'")

        val oAuthAttributes = OAuthAttributes.of(
            registrationId,
            userNameAttributeName,
            oAuth2User.attributes
        )

        val loginUser : User = authProcessor.process(oAuthAttributes)

        return DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(loginUser.role.role)),
            oAuthAttributes.attributes,
            oAuthAttributes.userNameAttributeName
        )
    }

}
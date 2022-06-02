package site.hirecruit.hr.domain.auth.service

import mu.KotlinLogging
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * Spring Context 접근에 대한 Service
 *
 * @author 정시원
 * @since 1.0
 */
@Service
class SecurityContextAccessService{

    fun updateAuthentication(authUserInfo: AuthUserInfo){
        val oldOAuth2AuthenticationToken = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
        val oldOAuth2User = oldOAuth2AuthenticationToken.principal as DefaultOAuth2User

        log.info("oauth2user name = '${oldOAuth2User.name}'")

        val newOAuth2User  = DefaultOAuth2User(
            Collections.singleton(SimpleGrantedAuthority(authUserInfo.role.role)),
            oldOAuth2User.attributes,
            "id"
        )
        val newOAuth2AuthenticationToken = OAuth2AuthenticationToken(
            newOAuth2User,
            newOAuth2User.authorities,
            oldOAuth2AuthenticationToken.authorizedClientRegistrationId
        )

        SecurityContextHolder.getContext().authentication = newOAuth2AuthenticationToken
    }

}
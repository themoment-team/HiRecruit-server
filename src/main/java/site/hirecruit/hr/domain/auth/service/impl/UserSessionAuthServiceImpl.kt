package site.hirecruit.hr.domain.auth.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.OAuthAttributes
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.domain.auth.repository.UserRepository
import site.hirecruit.hr.domain.auth.service.UserAuthService
import site.hirecruit.hr.global.data.SessionAttribute
import javax.servlet.http.HttpSession

/**
 * OAuth2 client의 정보를 기반으로 인증하는 서비스
 *
 * @author 정시원
 * @since 1.0
 */
@Service
open class UserSessionAuthServiceImpl(
    private val userRepository: UserRepository,
    private val tempUserRepository: TempUserRepository,
) : UserAuthService {

    override fun authentication(oAuthAttributes: OAuthAttributes): AuthUserInfo {
        return  if(tempUserRepository.existsById(oAuthAttributes.id))
                    createAuthUserInfoWithTempUserEntity(oAuthAttributes)
                else
                    createAuthUserInfoWithUserEntity(oAuthAttributes)
    }

    private fun createAuthUserInfoWithUserEntity(oAuthAttributes: OAuthAttributes): AuthUserInfo {
        return userRepository.findUserAndWorkerEmailByGithubId(oAuthAttributes.id)
            ?: throw OAuth2AuthenticationException("해당 oauth정보로 회원을 찾을 수 없습니다. [githubId = '${oAuthAttributes.id}']")
    }

    private fun createAuthUserInfoWithTempUserEntity(oAuthAttributes: OAuthAttributes): AuthUserInfo{
        val tempUserEntity = tempUserRepository.findByIdOrNull(oAuthAttributes.id)
            ?: throw OAuth2AuthenticationException("임시 회원의 유효기간이 만료되었거나, 잘못된 회원 정보입니다.")
        return AuthUserInfo(
            githubId = tempUserEntity.githubId,
            name = tempUserEntity.name,
            email = null,
            role = tempUserEntity.role,
            profileImgUri = tempUserEntity.profileImgUri
        )
    }
}
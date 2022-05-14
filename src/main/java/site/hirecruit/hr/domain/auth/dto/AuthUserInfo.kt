package site.hirecruit.hr.domain.auth.dto

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import site.hirecruit.hr.domain.auth.entity.Role

/**
 * 인증/인가 시 유저의 정보를 담고 있는 객체
 *
 * 초기 버전은 세션으로 유저의 정보를 담습니다.
 *
 * @author 정시원
 * @version 1.0
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
open class AuthUserInfo(
    val githubId: Long,
    val name: String,
    val email: String?,
    val profileImgUri: String,
    val role: Role
): java.io.Serializable {
    override fun toString(): String {
        return "AuthUserInfo(githubId=$githubId, name='$name', email=$email, profileUri='$profileImgUri', role=$role)"
    }
}
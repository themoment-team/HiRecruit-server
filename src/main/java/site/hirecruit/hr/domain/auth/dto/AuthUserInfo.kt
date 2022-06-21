package site.hirecruit.hr.domain.auth.dto

import com.querydsl.core.annotations.QueryProjection
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
open class AuthUserInfo @QueryProjection constructor(
    val githubId: Long,
    val githubLoginId: String,
    val name: String,
    val email: String?,
    val profileImgUri: String,
    val role: Role
): java.io.Serializable {
    override fun toString(): String {
        return "AuthUserInfo(githubId=$githubId, name='$name', email=$email, profileUri='$profileImgUri', role=$role)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthUserInfo) return false

        if (githubId != other.githubId) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (profileImgUri != other.profileImgUri) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = githubId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + profileImgUri.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }

}
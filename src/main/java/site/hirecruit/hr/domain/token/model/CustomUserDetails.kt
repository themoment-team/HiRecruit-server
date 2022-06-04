package site.hirecruit.hr.domain.token.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import java.util.*

/**
 * UserDetalis를 custom함
 *
 * @author 정시원
 */
data class CustomUserDetails(
    val authUserInfo: AuthUserInfo
): UserDetails {

    override fun getAuthorities(): Collection<out GrantedAuthority> =
        Collections.singleton(SimpleGrantedAuthority(this.authUserInfo.role.role))

    override fun getPassword(): String? = null

    override fun getUsername(): String = this.authUserInfo.name

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
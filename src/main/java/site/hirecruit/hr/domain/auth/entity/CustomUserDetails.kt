package site.hirecruit.hr.domain.auth.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * username
 * @see UserEntity.profileImgUri
 *
 * password
 * @see UserEntity.githubId
 */
class CustomUserDetails(
    private val profileImgUri : String,
    private val githubId : Long,
    private val authorities : MutableCollection<out GrantedAuthority>,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities
    }

    override fun getUsername(): String {
        return this.profileImgUri
    }

    override fun getPassword(): String {
        return this.githubId.toString()
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
package site.hirecruit.hr.global.security.service

import mu.KotlinLogging
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.repository.UserRepository

private val log = KotlinLogging.logger {}

@Component
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    /**
     * username == profileImgUri
     * password == githubId
     */
    override fun loadUserByUsername(profileImgUri: String): UserDetails {
        log.info { "Authenticating user profileImgUrl: $profileImgUri" }

        // profileImg is unique and string
        val userEntity = (userRepository.findByProfileImgUri(profileImgUri)
            ?: throw UsernameNotFoundException("spring security username: $profileImgUri is not found in database"))

        // get and set role
        val grantedAuthorities: ArrayList<GrantedAuthority> = ArrayList()
        grantedAuthorities.add(SimpleGrantedAuthority(userEntity.role.name))

        // return UserDetails
        return User(userEntity.profileImgUri, userEntity.githubId.toString(), grantedAuthorities)
    }
}
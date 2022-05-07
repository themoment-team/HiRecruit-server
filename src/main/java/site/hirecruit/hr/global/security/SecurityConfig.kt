package site.hirecruit.hr.global.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User

@Configuration
class SecurityConfig(
    private val oauth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>
    ) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().disable()

        http
            .authorizeRequests()
            .antMatchers(
                "/", "/css/**", "/images/**",
                "/js/**", "/h2-console/**"
            ).permitAll() //                .antMatchers("/api/v1/**")
            //                .hasRole(WorkerEntity.Role.CLIENT.name())
            .anyRequest().permitAll()

        http
            .logout()
            .logoutSuccessUrl("/")

        http
            .oauth2Login()
            .userInfoEndpoint()
            .userService(oauth2UserService)
    }
}
package site.hirecruit.hr.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer
import site.hirecruit.hr.domain.auth.entity.Role

/**
 * default, test과 같이 주로 테스트 환경에서 사용되는 security config입니다.
 *
 * 운영환경에서 사용하는 것을 권장하지 않습니다.
 *
 * @author 정시원
 * @since 1.0
 */
@Configuration
@Profile("default", "local", "prod-test")
class SecurityConfig(
    private val oauth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    private val successHandler: AuthenticationSuccessHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().disable()

        http
            .sessionManagement {
                it.maximumSessions(1)
                    .maxSessionsPreventsLogin(false) // 만약 중복 로그인이 발생하면 처음 로그인한사람이 로그아웃된다.
                it.sessionFixation()
                    .none()
            }

        http
            .authorizeRequests()
            .antMatchers(
                "/", "/css/**", "/images/**",
                "/js/**", "/h2-console/**"
            ).permitAll()
                // 권한 테스트용 end point
            .antMatchers("/test/email").hasRole(Role.UNAUTHENTICATED_EMAIL.name)
            .antMatchers("/test/client").hasRole(Role.CLIENT.name)
            .antMatchers("/test/guest").hasRole(Role.GUEST.name)
            .anyRequest().permitAll()

        http
            .logout()
            .logoutSuccessUrl("/")

        http
            .oauth2Login()
            .userInfoEndpoint()
            .userService(this.oauth2UserService)

        http.oauth2Login { oauth2Login ->
            oauth2Login.authorizationEndpoint{ endpoint ->
                endpoint.baseUri("/api/v1/auth/oauth2/authorization") // oauth2 로그인 최초 진입점
            }
            oauth2Login.redirectionEndpoint{
                it.baseUri("/api/v1/auth/oauth2/redirection-endpoint")
            }
            oauth2Login.successHandler(successHandler)
        }
    }
}
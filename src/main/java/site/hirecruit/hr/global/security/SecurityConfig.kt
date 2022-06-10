package site.hirecruit.hr.global.security

import org.apache.tomcat.util.descriptor.web.SecurityRoleRef
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.global.data.ServerProfile

/**
 * SecurityConfig
 *
 * inner class로 프로필별 설정을 관리합니다.
 *
 * @author 정시원
 * @since 1.0
 */
@Configuration
class SecurityConfig(
    private val oauth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val logoutSuccessHandler: LogoutSuccessHandler
) {

    private val oauth2LoginEndpointBaseUri = "/api/v1/auth/oauth2/authorization"
    private val oauth2LoginRedirectionEndpointBaseUri = "/api/v1/auth/oauth2/redirection-endpoint"

    private fun logoutConfig(http: HttpSecurity){
        http
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .logoutSuccessHandler(logoutSuccessHandler)
    }
    /**
     * 운영환경(prod)에서 활성화 되는 SecurityConfig
     *
     * @author 정시원
     * @since 1.0
     */
    @Configuration
    @Profile(ServerProfile.PROD)
    inner class ProdRole: WebSecurityConfigurerAdapter(){
        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .headers().frameOptions().disable()

            http
                .authorizeRequests{
                    it.antMatchers(
                        "/api/v1/worker/me",
                        "/api/v1/worker/me/**"
                    ).hasAnyRole(Role.UNAUTHENTICATED_EMAIL.role, Role.CLIENT.role)
                    it.antMatchers(
                        "/api/v1/auth/registration"
                    ).hasRole(Role.GUEST.role)
                    it.anyRequest().permitAll()
                }

            logoutConfig(http)

            http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oauth2UserService)

            http.oauth2Login { oauth2Login ->
                oauth2Login.authorizationEndpoint{ endpoint ->
                    endpoint.baseUri(oauth2LoginEndpointBaseUri) // oauth2 로그인 최초 진입점
                }
                oauth2Login.redirectionEndpoint{
                    it.baseUri(oauth2LoginRedirectionEndpointBaseUri)
                }
                oauth2Login.successHandler(authenticationSuccessHandler)
            }
        }
    }

    /**
     * 테스팅 환경(default, local, staging)에서 활성화 되는 SecurityConfig
     *
     * @author 정시원
     * @since 1.0
     */
    @Configuration
    @Profile(ServerProfile.DEFAULT, ServerProfile.LOCAL, ServerProfile.STAGING)
    inner class TestingRole: WebSecurityConfigurerAdapter(){
        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()

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

            logoutConfig(http)

            http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(oauth2UserService)

            http.oauth2Login { oauth2Login ->
                oauth2Login.authorizationEndpoint{ endpoint ->
                    endpoint.baseUri(oauth2LoginEndpointBaseUri) // oauth2 로그인 최초 진입점
                }
                oauth2Login.redirectionEndpoint{
                    it.baseUri(oauth2LoginRedirectionEndpointBaseUri)
                }
                oauth2Login.successHandler(authenticationSuccessHandler)
            }
        }
    }
}
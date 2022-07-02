package site.hirecruit.hr.global.security

import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.global.data.ServerProfile

private val log = KotlinLogging.logger {  }

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
    private val logoutSuccessHandler: LogoutSuccessHandler,
    private val authenticationFailureHandler: AuthenticationFailureHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) {

    private val oauth2LoginEndpointBaseUri = "/api/v1/auth/oauth2/authorization"
    private val oauth2LoginRedirectionEndpointBaseUri = "/api/v1/auth/oauth2/redirection-endpoint"

    private fun logoutConfig(http: HttpSecurity) = http
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .logoutSuccessHandler(logoutSuccessHandler)


    private fun authorizeRequests(http: HttpSecurity) = http
            .authorizeRequests{
                it.antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS를 위해 OPTION method는 모든 요청에 대해 권한없이 접근할 수 있다.
                it.antMatchers(
                    "/api/v1/worker/me",
                    "/api/v1/worker/me/**"
                ).hasAnyRole(Role.WORKER.name, Role.MENTOR.name)
                it.antMatchers(
                    "/api/v1/auth/registration"
                ).hasRole(Role.GUEST.name)
                it.antMatchers(
                    HttpMethod.PATCH, "/api/v1/user/me"
                ).hasAnyRole(Role.WORKER.name, Role.MENTOR.name)
                it.antMatchers(
                    "/api/v1/mentor/promotion/process/{workerId}",
                    "/api/v1/mentor/promotion/process/verify"
                ).hasRole(Role.WORKER.name)
                it.antMatchers(HttpMethod.POST, "/api/v1/company")
                    .authenticated()
                it.anyRequest().permitAll()
            }

    private fun accessDenied(http: HttpSecurity){
        http
            .exceptionHandling {
//                it.accessDeniedHandler()
                it.authenticationEntryPoint(authenticationEntryPoint)
            }
    }

    private fun cors(http: HttpSecurity){
        http.cors()
    }

    private fun oauth2Login(http: HttpSecurity) =
        http.oauth2Login{ oauth2Login ->
            oauth2Login.userInfoEndpoint{
                it.userService(oauth2UserService)
            }
            oauth2Login.authorizationEndpoint{ authorizationEndPoint ->
                authorizationEndPoint.baseUri(oauth2LoginEndpointBaseUri) // oauth2 로그인 최초 진입점
            }
            oauth2Login.redirectionEndpoint{ redirectEndPoint ->
                redirectEndPoint.baseUri(oauth2LoginRedirectionEndpointBaseUri)
            }
            oauth2Login.successHandler(authenticationSuccessHandler)
            oauth2Login.failureHandler(authenticationFailureHandler)
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

            cors(http)
            authorizeRequests(http)
            logoutConfig(http)
            accessDenied(http)
            oauth2Login(http)
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

        override fun configure(web: WebSecurity) {
            web
                /**
                 * antMatchers에 설정한 url에 대해 security 설정을 무시하는 설정
                 */
                .ignoring()
                .antMatchers("/h2-console/**")
        }

        override fun configure(http: HttpSecurity) {
            http.headers { headers ->
                headers.frameOptions {
                    it.sameOrigin()
                }
            }

            http
                .csrf().disable()

            http
                .authorizeRequests()
                .antMatchers(
                    "/", "/css/**", "/images/**",
                    "/js/**", "/h2-console/**"
                ).permitAll()

            authorizeRequests(http)
            logoutConfig(http)
            accessDenied(http)
            oauth2Login(http)
            cors(http)
        }
    }
}
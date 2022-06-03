package site.hirecruit.hr.global.security

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import site.hirecruit.hr.domain.auth.entity.Role

@Configuration
@Profile("auth-token")
class TokenSecurityConfig(

): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .headers().frameOptions().disable()

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

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

        http.oauth2Login { oauth2Login ->
            oauth2Login.authorizationEndpoint{ endpoint ->
                endpoint.baseUri("/api/v1/auth/oauth2/authorization") // oauth2 로그인 최초 진입점
            }
            oauth2Login.redirectionEndpoint{
                it.baseUri("/api/v1/auth/oauth2/redirection-endpoint")
            }
            oauth2Login.successHandler { _, response, _ ->
                response.sendRedirect("/api/v1/auth/oauth2/success") // oauth2 login에 성공하면 해당 uri로 redirect
            }
        }
    }

}
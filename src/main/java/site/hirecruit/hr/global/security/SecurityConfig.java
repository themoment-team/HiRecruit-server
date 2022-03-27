package site.hirecruit.hr.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import site.hirecruit.hr.domain.auth.service.CustomOAuth2UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**",
                        "/js/**", "/h2-console/**").permitAll()
//                .antMatchers("/api/v1/**")
//                .hasRole(WorkerEntity.Role.CLIENT.name())
                .anyRequest().permitAll();

        http
                .logout()
                .logoutSuccessUrl("/");

        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

}

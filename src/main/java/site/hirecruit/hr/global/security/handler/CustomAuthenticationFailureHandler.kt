package site.hirecruit.hr.global.security.handler

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.util.UriComponentsBuilder
import site.hirecruit.hr.global.util.CookieMakerUtil
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = KotlinLogging.logger {  }

/**
 * Security 인증 실패 핸들러
 *
 * @author 정시원
 * @since 1.1.2
 */
@Component
class CustomAuthenticationFailureHandler(
    @Value("\${oauth2.login.success.redirect-base-uri}") val redirectBaseUri: String,
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver,
    @Value("\${server.servlet.session.cookie.domain}") val cookieDomain: String
): AuthenticationFailureHandler {

    /**
     * Authentication 실패시 예외를 핸들링 하는 메서드
     * OAuth2 인증 예외라면 [SecurityExceptionHandler.oauth2AuthenticationException]에서 예외를 헨들링한다.
     * 에외처리는 Hirecruit GET /api/v1/auth/oauth2/authorization/github API 명세를 확인해주세요
     *
     * @see SecurityExceptionHandler.oauth2AuthenticationException
     * @param exception Authentication실패에 대한 예외
     */
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val redirectUriBuilder = UriComponentsBuilder.fromUriString(redirectBaseUri)
        response.addCookie(CookieMakerUtil.deleteHrsessionCookie(cookieDomain)) // OAuth2 login 실패시 HRSESSION제거
        if(exception is OAuth2AuthenticationException){ // OAuth2 로그인 예외라면
            log.debug { "OAuth2AuthenticationException error code = '${exception.error.errorCode}'" }
            oAuth2AuthenticationExceptionHandling(request, response, exception)
        } else{
            unknownExceptionHandling(response, redirectUriBuilder)
            log.error(exception){ "Unknown Authentication Exception"}
        }
    }

    private fun oAuth2AuthenticationExceptionHandling(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: OAuth2AuthenticationException
    ){
        resolver.resolveException(request, response, null, ex)
    }

    private fun unknownExceptionHandling(response: HttpServletResponse, redirectUrlBuilder: UriComponentsBuilder){
        response.sendRedirect(
            redirectUrlBuilder
                .queryParam("login", "fail")
                .queryParam("server_error", true)
                .build().toUriString()
        )
    }
}
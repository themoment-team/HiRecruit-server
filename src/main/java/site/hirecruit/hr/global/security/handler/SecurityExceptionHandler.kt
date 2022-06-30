package site.hirecruit.hr.global.security.handler

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.util.UriComponentsBuilder
import site.hirecruit.hr.global.exception.model.ExceptionResponseEntity
import javax.servlet.http.HttpServletResponse

private val log = KotlinLogging.logger {  }

/**
 * Security관련 exception handler
 *
 * @author 정시원
 * @since 1.0
 */
@RestControllerAdvice
class SecurityExceptionHandler(
    @Value("\${oauth2.login.success.redirect-base-uri}") val redirectBaseUri: String,
) {

    /**
     * Spring Security Firewall에 의해 block된 요청을 헨들링 하는 메서드
     *
     * @see site.hirecruit.hr.global.exception.filter.ExceptionResolverFilter.doFilterInternal
     */
    @ExceptionHandler(RequestRejectedException::class)
    private fun requestRejectedException(requestRejectedException: RequestRejectedException): ResponseEntity<ExceptionResponseEntity> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(ExceptionResponseEntity(requestRejectedException.localizedMessage))

    @ExceptionHandler(AuthenticationException::class)
    private fun authenticationException(authenticationException: AuthenticationException): ResponseEntity<ExceptionResponseEntity> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
            .body(ExceptionResponseEntity.of(authenticationException))

    /**
     * Oauth2 인증이 실패할 떄 발생하는 exception
     */
    @ExceptionHandler(OAuth2AuthenticationException::class)
    private fun oauth2AuthenticationException(
        ex: OAuth2AuthenticationException,
        response: HttpServletResponse
    ) {
        val redirectUrlBuilder = UriComponentsBuilder.fromUriString(redirectBaseUri)
        log.debug { "Oauth2AuthenticationExceptionHandler Active" }
        when(ex.error.errorCode){
            OAuth2ErrorCodes.ACCESS_DENIED -> { // 유저가 Oauth2 login동의를 하지 않을 때
                log.info { "OAuth2ErrorCodes.${OAuth2ErrorCodes.ACCESS_DENIED}"}
                redirectUrlBuilder.queryParam("login", "cancel")
            }
            "authorization_request_not_found" -> {
                log.info { """
                    authorization_request_not_found 발생 
                """.trimIndent() }
                redirectUrlBuilder.queryParam("login", "fail")
                redirectUrlBuilder.queryParam("server_error", false)
            }else -> {
                log.error(ex){ """
                    예외 처리하지 않은 OAuth2 Exception 발생
                    error details={
                        error_code = '${ex.error.errorCode}',
                        error_description = '${ex.error.description}'
                    }
                """.trimIndent() } //
                redirectUrlBuilder.queryParam("login", "fail")
                redirectUrlBuilder.queryParam("server_error", true)
            }
        }
        response.sendRedirect(redirectUrlBuilder.build().toUriString())
    }

}
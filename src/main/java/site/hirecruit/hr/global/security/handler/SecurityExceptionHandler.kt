package site.hirecruit.hr.global.security.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import site.hirecruit.hr.global.exception.model.ExceptionResponseEntity

/**
 * Security관련 exception handler
 *
 * @author 정시원
 * @since 1.0
 */
@RestControllerAdvice
class SecurityExceptionHandler {

    /**
     * Spring Security Firewall에 의해 block된 요청을 헨들링 하는 메서드
     *
     * @see site.hirecruit.hr.global.exception.filter.ExceptionResolverFilter.doFilterInternal
     */
    @ExceptionHandler(RequestRejectedException::class)
    private fun requestRejectedException(requestRejectedException: RequestRejectedException): ResponseEntity<ExceptionResponseEntity> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(ExceptionResponseEntity(requestRejectedException.localizedMessage))
}
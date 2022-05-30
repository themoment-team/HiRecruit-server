package site.hirecruit.hr.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import site.hirecruit.hr.global.exception.model.ExceptionResponseEntity

/**
 * Global한 예외를 핸들링 하는 Handler입니다.
 *
 * @author 정시원
 * @since 1.0
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpStatusCodeException::class)
    private fun httpStatusException(ex: HttpClientErrorException): ResponseEntity<ExceptionResponseEntity> =
        ResponseEntity.status(ex.statusCode.value())
            .body(ExceptionResponseEntity.of(ex))

    @ExceptionHandler(IllegalArgumentException::class)
    private fun illegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ExceptionResponseEntity> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(ExceptionResponseEntity.of(ex))
}
package site.hirecruit.hr.global.exception

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import site.hirecruit.hr.global.exception.model.ExceptionResponseEntity

private val log = KotlinLogging.logger {  }

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

    /**
     * Spring Bean Validation Handler
     *
     * 반환 메세지 예시
     * ```json
     * {
     *    "message": "'name':'공백일 수 없습니다.', 'homepageUri':'올바른 URL이어야 합니다.', "해당 company(회사)는 이미 존재합니다."
     * }
     * ```
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun validationException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionResponseEntity> {
        val statusCode = HttpStatus.BAD_REQUEST.value()

        val bindingResult: BindingResult = ex.bindingResult
        log.debug { "BindResult = '$bindingResult'" }

        val errorResultBuilder = StringBuilder()

        /**
         * objectError메시지 전달
         */
        for (objectError in bindingResult.globalErrors){
            val errorMessage = objectError.defaultMessage

            errorResultBuilder.append("'${errorMessage}.'")
            errorResultBuilder.append(", ")
        }


        for (fieldError in bindingResult.fieldErrors) {
            /**
             * 유효성검사에 통과하지 못한 field name, field name의 접두사에 "_"가 있다면 제거한다.
             * "Dto._"문자를 "."로 치환한다.
             */
            val fieldErrorName = fieldError.field
                .removePrefix("_")
                .replace("Dto._", ".")

            /**
             * 유효성 검사에 통과하지 못한 field에 대한 메시지
             */
            val errorMessage = fieldError.defaultMessage

            errorResultBuilder.append("'$fieldErrorName'").append(":")
            errorResultBuilder.append("'${errorMessage}.'")
            errorResultBuilder.append(", ")
        }

        val errorResult = errorResultBuilder.removeSuffix(", ").toString() // 마지막 리스트일 경우  ", " 문자열을 제거한다.
        return ResponseEntity.status(statusCode)
            .body(ExceptionResponseEntity(errorResult))
    }
}
package site.hirecruit.hr.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
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

    /**
     * Spring Bean Validation Handler
     *
     * 반환 메세지 예시
     * ```json
     * {
     *    "message": "'name':'공백일 수 없습니다.', 'homepageUri':'올바른 URL이어야 합니다.', 'location':'공백일 수 없습니다.', 'companyImgUri':'공백일 수 없습니다.'"
     * }
     * ```
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    private fun validationException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionResponseEntity>{
        val bindingResult: BindingResult = ex.bindingResult

        val errorResult = StringBuilder()
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

            errorResult.append("'$fieldErrorName'").append(":")
            errorResult.append("'${errorMessage}.'")
            errorResult.append(", ")
        }
        errorResult.delete(errorResult.lastIndexOf(", "), errorResult.lastIndex + 1) // 마지막 리스트일 경우  ", " 문자열을 제거한다.

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(ExceptionResponseEntity(errorResult.toString()))
    }
}
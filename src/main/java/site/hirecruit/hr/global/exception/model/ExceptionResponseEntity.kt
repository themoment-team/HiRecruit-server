package site.hirecruit.hr.global.exception.model

/**
 * Exception발생시 클라이언트에 반환할 Response 객체입니다.
 *
 * 해당 클래스는 정적 팩토리 매서드로도 생성할 수 있습니다. 다음은 Spring RestControllerAdvice로 예외를 핸들링하는 예제입니다..
 * ```kotlin
 *  @ExceptionHandler(HttpStatusCodeException::class)
 *  private fun httpStatusException(ex: HttpClientErrorException): ResponseEntity<ExceptionResponseEntity> =
 *      ResponseEntity.status(ex.statusCode.value())
 *          .body(ExceptionResponseEntity.of(ex))
 * ```
 * @see ExceptionResponseEntity.of
 * @author 정시원
 * @since 1.0
 */
class ExceptionResponseEntity (
    val message: String?
) {
    companion object{
        fun <T : Throwable> of(exception: T): ExceptionResponseEntity = ExceptionResponseEntity(exception.message)
    }
}
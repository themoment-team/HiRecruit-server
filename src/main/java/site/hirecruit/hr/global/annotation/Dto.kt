package site.hirecruit.hr.global.annotation

/**
 * Dto 어노테이션을 클래스에 달면 noArg 가 생성됩니다.
 * @author 전지환
 * @since 1.0.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Dto

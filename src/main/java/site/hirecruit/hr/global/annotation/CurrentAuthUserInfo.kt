package site.hirecruit.hr.global.annotation

/**
 * 해당 annotation을 통해 현제 세션에 존재하고 있는 사용자의 정보([site.hirecruit.hr.domain.auth.dto.AuthUserInfo])를 contoller에서 가져올 수 있습니다.
 *
 * ## code example
 * ```kotlin
 * @GetMapping("/example")
 * fun example(@CurrentAuthUserInfo authUserInfo: AuthUserInfo) {
 *      ...
 * }
 * ```
 *
 * @author 정시원
 * @since 1.0
 * @see site.hirecruit.hr.global.resolver.CurrentAuthUserInfoResolver
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrentAuthUserInfo

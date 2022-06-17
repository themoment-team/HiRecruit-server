package site.hirecruit.hr.domain.company.validator.annoation

import site.hirecruit.hr.domain.company.validator.CompanyDuplicateValidator
import site.hirecruit.hr.domain.company.validator.CompanyExistValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * Company가 중복으로 존재하는지 검증하는 validator
 * 해당 annotation은 CompanyDto.Create에서만 사용할 수 있습니다.
 *
 * @see CompanyExistValidator
 * @author 정시원
 * @since 1.1.2
 */
@MustBeDocumented
@Constraint(validatedBy = [CompanyDuplicateValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CompanyNotDuplicate(
    val message: String = "해당 company(회사)는 이미 존재합니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

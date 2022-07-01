package site.hirecruit.hr.domain.company.validator.annoation

import site.hirecruit.hr.domain.company.validator.CompanyExistValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * CompanyId에 해당하는 company가 존재하는지 검증하는 validator
 *
 * @author 정시원
 * @since 1.0
 */
@MustBeDocumented
@Constraint(validatedBy = [CompanyExistValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CompanyIsNotExistByCompanyId(
    val message: String = "해당 Company를 찾을 수 없습니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

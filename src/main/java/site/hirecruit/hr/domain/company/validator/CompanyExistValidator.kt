package site.hirecruit.hr.domain.company.validator

import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.company.validator.annoation.CompanyIsNotExistByCompanyId
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * companyId로 company가 존재하는지 검증하는 Validator
 *
 * @author 정시원
 * @since 1.0
 */
@Component
class CompanyExistValidator(
    private val companyRepository: CompanyRepository
): ConstraintValidator<CompanyIsNotExistByCompanyId, Long> {

    /**
     * @param value Long으로 변환가능한 문자열
     */
    override fun isValid(value: Long?, context: ConstraintValidatorContext?): Boolean {
        if(value == null)
            return true

        val companyId: Long = value.toLong()
        return companyRepository.existsById(companyId)
    }
}
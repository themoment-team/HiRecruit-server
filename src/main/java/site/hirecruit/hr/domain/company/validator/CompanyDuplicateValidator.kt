package site.hirecruit.hr.domain.company.validator

import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.company.validator.annoation.CompanyNotDuplicate
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * [CompanyNotDuplicate] annotation에 대한 validator
 *
 * @author 정시원
 * @since 1.1.2
 */
@Component
class CompanyDuplicateValidator(
    private val companyRepository: CompanyRepository
): ConstraintValidator<CompanyNotDuplicate, CompanyDto.Create>
 {
     override fun isValid(createDto: CompanyDto.Create, context: ConstraintValidatorContext): Boolean {
         return companyRepository.existsByNameAndLocation(createDto.name, createDto.location).not()
     }

 }
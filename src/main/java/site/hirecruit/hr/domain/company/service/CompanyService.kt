package site.hirecruit.hr.domain.company.service

import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.company.mapper.CompanyEntityMapper
import site.hirecruit.hr.domain.company.mapper.CompanyInfoDtoMapper
import site.hirecruit.hr.domain.company.repository.CompanyRepository

/**
 * CompanyService
 * 요구사항이 주기적으로 변경될 예정이므로 불안전한 객체입니다.
 *
 * @author 정시원
 * @since 1.0
 */
@Service
class CompanyService(
    private val companyRepository: CompanyRepository
) {

    /**
     * 회사를 저장함
     *
     * @return 저장한 회사 정보
     */
    fun create(createDto: CompanyDto.Create): CompanyDto.Info{
        val savedCompanyEntity = companyRepository.save(
            CompanyEntityMapper.INSTANCE.toEntity(createDto)
        )
        return CompanyInfoDtoMapper.INSTANCE.toCompanyCreateDto(savedCompanyEntity)
    }

    /**
     * 모든 회사를 조회함
     */
    fun findAllCompanies(): List<CompanyDto.Info> = companyRepository.findAllCompanyInfoDtoBy()
}
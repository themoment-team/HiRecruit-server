package site.hirecruit.hr.domain.company.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.entity.CompanyEntity

/**
 * [CompanyDto.Info]의 mapper class
 *
 * @author 정시원
 * @since 1.2.4
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
interface CompanyInfoDtoMapper {

    companion object{
        val INSTANCE:CompanyInfoDtoMapper = Mappers.getMapper(CompanyInfoDtoMapper::class.java)
    }

    fun toCompanyCreateDto(companyEntity: CompanyEntity): CompanyDto.Info
}
package site.hirecruit.hr.domain.user.mapper

import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.user.dto.RegularUserRegistrationDto
import site.hirecruit.hr.domain.user.entity.Role
import site.hirecruit.hr.domain.user.entity.UserEntity

/**
 * UserEntity의 mapper class
 *
 * @author 정시원
 * @since 1.2.4
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface UserEntityMapper {

    companion object{
        val INSTANCE: UserEntityMapper = Mappers.getMapper(UserEntityMapper::class.java)
    }

    @Mappings(
        Mapping(target = "role", source = "role"),
        Mapping(target = "email", source = "registrationDto.userRegistrationInfo.email"),
        Mapping(target = "name", source = "registrationDto.userRegistrationInfo.name")
    )
    fun toEntity(registrationDto: RegularUserRegistrationDto, role: Role): UserEntity
}
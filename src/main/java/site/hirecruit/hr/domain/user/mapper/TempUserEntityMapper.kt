package site.hirecruit.hr.domain.user.mapper

import org.mapstruct.*
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.user.entity.TempUserEntity
import site.hirecruit.hr.domain.user.dto.TempUserRegistrationDto

/**
 * TempUserEntity의 mapper class
 *
 * @author 정시원
 * @since 1.2.4
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface TempUserEntityMapper {

    companion object{
        val INSTANCE: TempUserEntityMapper = Mappers.getMapper(TempUserEntityMapper::class.java)
    }

    fun toEntity(registrationDto: TempUserRegistrationDto): TempUserEntity
}
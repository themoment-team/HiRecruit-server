package site.hirecruit.hr.domain.user.mapper

import org.mapstruct.*
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.auth.entity.TempUserEntity
import site.hirecruit.hr.domain.user.dto.TempUserRegistrationDto

/**
 * UserEntity의 mapper class
 *
 * @author 정시원
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
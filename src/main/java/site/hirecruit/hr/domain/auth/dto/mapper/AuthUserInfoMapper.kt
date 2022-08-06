package site.hirecruit.hr.domain.auth.dto.mapper

import org.mapstruct.*
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.entity.TempUserEntity
import site.hirecruit.hr.domain.user.dto.TempUserRegistrationDto
import site.hirecruit.hr.domain.user.entity.UserEntity

/**
 * AuthUserInfo의 Mapper class
 *
 * @author 정시원
 * @since 1.2.4
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
)
interface AuthUserInfoMapper {

    companion object{
        val INSTANCE: AuthUserInfoMapper = Mappers.getMapper(AuthUserInfoMapper::class.java)
    }

    fun toAuthUserInfo(userEntity: UserEntity): AuthUserInfo

    @Mappings(
        Mapping(target = "role", expression = "java(Role.GUEST)"),
        Mapping(target = "name", expression = "java(\"임시유저\")"),
        Mapping(target = "email", expression = "java(null)")
    )
    fun toAuthUserInfo(tempUserEntity: TempUserEntity): AuthUserInfo

    @Mappings(
        Mapping(target = "role", expression = "java(Role.GUEST)"),
        Mapping(target = "name", expression = "java(\"임시유저\")"),
        Mapping(target = "email", expression = "java(null)")
    )
    fun toAuthUserInfo(tempUserRegistrationDto: TempUserRegistrationDto): AuthUserInfo

}
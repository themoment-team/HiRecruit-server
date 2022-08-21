package site.hirecruit.hr.domain.worker.mapper

import org.mapstruct.*
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

/**
 * [WorkerEntity]의 mapper class
 *
 * @author 정시원
 * @since 1.2.4
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface WorkerEntityMapper {

    companion object{
        val INSTANCE: WorkerEntityMapper = Mappers.getMapper(WorkerEntityMapper::class.java)
    }

    @Mappings(
        Mapping(target = "user", source = "userEntity"),
        Mapping(target = "company", source = "companyEntity"),
        Mapping(target = "workerId", ignore = true)
    )
    fun toEntity(registrationDto: WorkerDto.Registration, userEntity: UserEntity, companyEntity: CompanyEntity): WorkerEntity
}
package site.hirecruit.hr.domain.worker.mapper

import org.mapstruct.*
import org.mapstruct.factory.Mappers
import site.hirecruit.hr.domain.worker.dto.*
import site.hirecruit.hr.domain.worker.entity.WorkerEntity

/**
 * [WorkerDto.Info]의 mapper class
 *
 * @author 정시원
 * @since 1.2.4
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface WorkerInfoDtoMapper {

    companion object{
        val INSTANCE: WorkerInfoDtoMapper = Mappers.getMapper(WorkerInfoDtoMapper::class.java)
    }

    @Mappings(
        Mapping(target = "companyInfoDto", source = "workerEntity.company"),

        Mapping(target = "name", source = "workerEntity.user.name"),
        Mapping(target = "email", source = "workerEntity.user.email"),
        Mapping(target = "githubLoginId", source = "workerEntity.user.githubLoginId"),
        Mapping(target = "profileImgUri", source = "workerEntity.user.profileImgUri"),
        Mapping(target = "userType", source = "workerEntity.user.role")

    )
    fun toWorkerInfoDto(workerEntity: WorkerEntity): WorkerDto.Info
}
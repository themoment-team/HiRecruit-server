package site.hirecruit.hr.domain.career.dto

import io.swagger.annotations.ApiModel
import io.swagger.v3.oas.annotations.media.Schema
import site.hirecruit.hr.domain.career.CareerEntity
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.global.util.YnType
import java.time.LocalDate
import javax.validation.constraints.NotBlank

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
class CareerDto {

    @ApiModel("CareerCreateDto")
    data class Create(
        @field:NotBlank
        val companyId: Long,
        @field:NotBlank
        val position: String,
        @field:NotBlank
        val beginDate: LocalDate,
        @field:Schema(description = "nullable, default null :: 가장 최근의 커리어로 판단함.")
        val endDate: LocalDate? = null,
        @field:NotBlank
        val inOfficeYN: YnType,
        @field:NotBlank
        val disclosureStatus: YnType
    ){ }

    class Info(
        val careerId: Long,
        val companyInfo: CompanyDto.Info,
        val position: String,
        val beginDate: LocalDate,
        val endDate: LocalDate? = null,
        val inOfficeYN: YnType,
        val disclosureStatus: YnType,
        val deleteStatus: YnType
    ){
        companion object{
            fun of(careerEntity: CareerEntity): Info {

                careerEntity.careerId ?: throw RuntimeException("careerId는 null이 허용되지 않습니다.")
                careerEntity.company.companyId ?: throw RuntimeException("companyId는 null이 허용되지 않습니다.")

                return Info(
                    careerId = careerEntity.careerId!!,
                    companyInfo = careerEntity.company.run {
                        CompanyDto.Info(
                            companyId = companyId!!,
                            name = name,
                            location = location,
                            homepageUri = homepageUri,
                            companyImgUri = companyImgUri
                        )
                    },
                    position = careerEntity.position,
                    beginDate = careerEntity.beginDate,
                    endDate = careerEntity.endDate,
                    inOfficeYN = careerEntity.inOfficeYN,
                    disclosureStatus = careerEntity.disclosureStatus,
                    deleteStatus = careerEntity.deleteStatus
                )
            }
        }
    }

}

package site.hirecruit.hr.domain.career.dto

import io.swagger.annotations.ApiModelProperty
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.global.util.YnType
import java.time.LocalDate

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
class CareerDto {

    class CareerCreateRequestDto(
        @ApiModelProperty(value = "companyId", position = 1)
        val companyId: Long,
        @ApiModelProperty(value = "position", position = 2)
        val position: String,
        @ApiModelProperty(value = "beginDate", position = 3)
        val beginDate: LocalDate,
        @ApiModelProperty(value = "endDate", position = 4)
        val endDate: LocalDate,
        @ApiModelProperty(value = "inOfficeYN", position = 4)
        val inOfficeYN: YnType,
        @ApiModelProperty(value = "disclosureStatus", position = 5)
        val disclosureStatus: YnType
    ){ }

    class Info(
        val careerId: Long,
        val companyInfo: CompanyDto.Info,
        val position: String,
        val beginDate: LocalDate,
        val endDate: LocalDate,
        val inOfficeYN: YnType,
        val disclosureStatus: YnType,
        val deleteStatus: YnType
    ){ }

}

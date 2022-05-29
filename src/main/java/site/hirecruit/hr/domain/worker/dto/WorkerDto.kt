package site.hirecruit.hr.domain.worker.dto

import com.fasterxml.jackson.annotation.JsonProperty
import site.hirecruit.hr.domain.company.dto.CompanyDto
import javax.validation.constraints.NotEmpty

/**
 * Worker 도메인 DTO
 *
 * @author 정시원
 * @since 1.0
 */
class WorkerDto {

    data class Registration(
        val companyId: Long,
        val giveLink: String? = null,
        val introduction: String? = null,
        val devYear: Int? = null,
        val position: String? = null,
    )

    data class Info(
        val name: String,

        val email: String,

        val introduction: String?,

        val profileImgUri: String,

        val giveLink: String?,

        val devYear: Int?,

        val position: String?,

        @field:JsonProperty("worker")
        val companyInfoDto: CompanyDto.Info
    )

    data class Update(
        val companyId: Long? = null,

        val introduction: String? = null,

        val giveLink: String? = null,

        val devYear: Int? = null,

        val position: String? = null,

        @NotEmpty
        val updateColumns: List<Column> = emptyList()
    ){

        enum class Column{
            COMPANY_ID, INTRODUCTION, GIVE_LINK, DEV_YEAR, POSITION
        }
    }
}
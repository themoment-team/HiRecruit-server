package site.hirecruit.hr.domain.worker.dto

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection
import org.hibernate.validator.constraints.URL
import site.hirecruit.hr.domain.company.dto.CompanyDto
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * Worker 도메인 DTO
 *
 * @author 정시원
 * @since 1.0
 */
class WorkerDto {

    data class Registration(
        @field:JsonProperty("companyId") @get:JsonGetter("companyId")
        @field:NotNull @field:Min(1)
        val _companyId: Long?, // validation 을 사용하기 위해 추가
        val giveLink: String? = null,
        val introduction: String? = null,
        val devYear: Int? = null,
        val position: String? = null,
    ){
        val companyId get() = _companyId!!
    }

    data class Info @QueryProjection constructor (
        val workerId: Long,

        val name: String,

        val email: String,

        val profileImgUri: String,

        val introduction: String?,

        val giveLink: String?,

        val devYear: Int?,

        val position: String?,

        @field:JsonProperty("company")
        val companyInfoDto: CompanyDto.Info
    )

    data class Update(
        val companyId: Long? = null,

        val introduction: String? = null,

        @field:URL
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
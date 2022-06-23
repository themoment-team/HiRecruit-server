package site.hirecruit.hr.domain.worker.dto

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.validator.annoation.CompanyIsNotExistByCompanyId
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import kotlin.math.max

/**
 * Worker 도메인 DTO
 *
 * @author 정시원
 * @since 1.0
 */
class WorkerDto {

    data class Registration(
        @field:JsonProperty("companyId") @get:JsonGetter("companyId")
        @field:NotNull @field:Min(1) @field:CompanyIsNotExistByCompanyId
        val _companyId: Long?, // validation 을 사용하기 위해 추가

        @field:URL
        val giveLink: String? = null,

        @field:Length(min = 0, max = 100)
        val introduction: String? = null,

        @field:Min(0) @field:Max(50)
        val devYear: Int? = null,

        @field:NotEmpty
        val position: String? = null,
    ){
        val companyId get() = _companyId!!
    }

    data class Info @QueryProjection constructor (
        val workerId: Long,

        val githubLoginId: String,

        val name: String,

        val email: String,

        val profileImgUri: String,

        val introduction: String?,

        val giveLink: String?,

        val devYear: Int?,

        val position: String?,

        val userType: Role,

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
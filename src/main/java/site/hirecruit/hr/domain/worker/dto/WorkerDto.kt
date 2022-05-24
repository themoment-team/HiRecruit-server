package site.hirecruit.hr.domain.worker.dto

import com.fasterxml.jackson.annotation.JsonUnwrapped
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Worker 도메인 DTO
 *
 * @author 정시원
 * @since 1.0
 */
class WorkerDto {

    data class Registration(
        @field:NotBlank
        val company: String,

        @field:NotBlank
        val location: String,

        val introduction: String? = null,
        val devYear: Int? = null
    )

    data class Info(
        @field:JsonUnwrapped
        val authUserInfo: AuthUserInfo,

        val company: String,

        val location: String,

        val introduction: String? = null,

        val devYear: Int? = null
    )

    data class Update(
        @field:NotBlank
        val company: String,

        @field:NotBlank
        val location: String,

        val introduction: String? = null,

        val devYear: Int? = null,

        val updateColumns: List<Column> = emptyList()
    ){

        enum class Column{
            COMPANY, LOCATION, INTRODUCTION, DEV_YEAR
        }
    }
}
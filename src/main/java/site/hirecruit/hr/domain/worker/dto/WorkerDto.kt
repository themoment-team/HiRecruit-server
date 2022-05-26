package site.hirecruit.hr.domain.worker.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

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

        val giveLink: String? = null,
        val introduction: String? = null,
        val devYear: Int? = null
    )

    data class Info(
        val name: String,

        val email: String,

        val profileImgUri: String,

        val company: String,

        val location: String,

        val introduction: String? = null,

        val giveLink: String? = null,

        val devYear: Int? = null
    )

    data class Update(
        val company: String? = null,

        val location: String? = null,

        val introduction: String? = null,

        val giveLink: String? = null,

        val devYear: Int? = null,

        @NotEmpty
        val updateColumns: List<Column> = emptyList()
    ){

        enum class Column{
            COMPANY, LOCATION, INTRODUCTION, GIVE_LINK, DEV_YEAR
        }
    }
}
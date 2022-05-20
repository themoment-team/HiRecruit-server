package site.hirecruit.hr.domain.worker.dto

import javax.validation.constraints.NotBlank

class WorkerDto {

    data class Registration(
        @field:NotBlank
        val company: String,

        @field:NotBlank
        val location: String,

        val introduction: String? = null,
        val devYear: Int? = null
    )
}
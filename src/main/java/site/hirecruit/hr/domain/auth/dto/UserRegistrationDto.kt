package site.hirecruit.hr.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRegistrationDto(
    @field:NotBlank @field:Email
    val email: String,

    val name: String? = null,

    @field:JsonProperty("worker") @field:NotNull
    val workerDto: WorkerDto.Registration
)
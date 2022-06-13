package site.hirecruit.hr.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRegistrationDto(
    @field:NotBlank @field:Email
    val email: String,

    val name: String,

    @field:JsonProperty("worker") @field:NotNull @field:Valid
    val workerDto: WorkerDto.Registration
)
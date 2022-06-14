package site.hirecruit.hr.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserRegistrationDto(
    @field:NotBlank @field:Email
    val _email: String?,

    @field:NotBlank
    var _name: String?,

    @field:JsonProperty("worker") @field:NotNull @field:Valid
    val workerDto: WorkerDto.Registration
){
    val email get() = _email!!
    val name get() = _name!!
}
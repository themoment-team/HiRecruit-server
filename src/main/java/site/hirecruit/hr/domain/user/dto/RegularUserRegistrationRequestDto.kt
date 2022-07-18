package site.hirecruit.hr.domain.user.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * 사용자 등록 VO
 *
 * @author 정시원
 * @since 1.3
 */
data class RegularUserRegistrationRequestDto(

    @field:NotBlank @field:Email
    @field:JsonProperty("email")
    private val _email: String?,

    @field:NotBlank
    @field:JsonProperty("name")
    private var _name: String?,

    @field:JsonProperty("worker") @field:NotNull @field:Valid
    val workerDto: WorkerDto.Registration
) {

    @get:JsonIgnore
    val email get() = _email!!

    @get:JsonIgnore
    val name get() = _name!!
}
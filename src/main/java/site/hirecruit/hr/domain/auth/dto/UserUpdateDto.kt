package site.hirecruit.hr.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * 유저 정보 Update DTO
 *
 * @author 정시원
 * @since 1.0
 */
data class UserUpdateDto(

    @field:NotBlank @field:Email
    @field:JsonProperty("email")
    val _email: String?,

    @field:NotBlank @field:JsonProperty("name")
    var _name: String?,
) {
    @get:JsonIgnore
    val email get() = _email!!
    @get:JsonIgnore
    val name get() = _name!!
}
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

    @field:Email @field:NotBlank
    @field:JsonProperty("email")
    private var _email: String?,

    @field:JsonProperty("name") @NotBlank
    private var _name: String?,
){
    @get:JsonIgnore
    val email: String get() = _email!!

    @get:JsonIgnore
    val name: String get() = _name!!
}
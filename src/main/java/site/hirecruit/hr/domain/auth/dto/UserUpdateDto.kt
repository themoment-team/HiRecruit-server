package site.hirecruit.hr.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email

/**
 * 유저 정보 Update DTO
 *
 * @author 정시원
 * @since 1.0
 */
data class UserUpdateDto(

    @field:Email
    @field:JsonProperty("email")
    var email: String?,

    @field:JsonProperty("name")
    var name: String?,

    val updateColumns: List<Column> = emptyList()
) {

    enum class Column{
        EMAIL, NAME
    }
}
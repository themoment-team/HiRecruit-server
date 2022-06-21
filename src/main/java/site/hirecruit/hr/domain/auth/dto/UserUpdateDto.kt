package site.hirecruit.hr.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import site.hirecruit.hr.domain.worker.dto.WorkerDto
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

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

    @NotEmpty
    val updateColumns: List<Column> = emptyList()
) {

    enum class Column{
        EMAIL, NAME
    }
}
package site.hirecruit.hr.domain.company.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection
import org.hibernate.validator.constraints.URL
import site.hirecruit.hr.domain.company.validator.annoation.CompanyNotDuplicate
import javax.validation.constraints.NotBlank

class CompanyDto {

    data class Info @QueryProjection constructor(
        val companyId: Long,
        val name: String,
        val location: String,
        val homepageUri: String?,
        val companyImgUri: String?
    )

    @CompanyNotDuplicate
    data class Create(
        @field:JsonProperty("name") @field:NotBlank
        val _name: String?,

        @field:JsonProperty("location") @field:NotBlank
        val _location: String?,

        @field:JsonProperty("homepageUri") @field:URL
        private val _homepageUri: String?,

        @field:JsonProperty("companyImgUri") @field:NotBlank @field:URL
        private val _companyImgUri: String?
    ){
        @get:JsonIgnore
        val name: String get() = _name!!

        @get:JsonIgnore
        val location: String get() = _location!!

        @get:JsonIgnore
        val homepageUri: String? get() = _homepageUri

        @get:JsonIgnore
        val companyImgUri: String get() = _companyImgUri!!
    }

}

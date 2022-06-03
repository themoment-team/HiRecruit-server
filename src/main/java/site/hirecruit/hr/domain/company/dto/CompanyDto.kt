package site.hirecruit.hr.domain.company.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.querydsl.core.annotations.QueryProjection
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

class CompanyDto {

    data class Info @QueryProjection constructor(
        val companyId: Long,
        val name: String,
        val location: String,
        val homepageUri: String?,
        val companyImgUri: String?
    )

    data class Create(
        @field:JsonProperty("name") @field:NotBlank
        private val _name: String?,

        @field:JsonProperty("location") @field:NotBlank
        private val _location: String?,

        @field:JsonProperty("homepageUri") @field:URL
        private val _homepageUri: String?,

        @field:JsonProperty("companyImgUri") @field:NotBlank @field:URL
        private val _companyImgUri: String?
    ){
        val name: String get() = _name!!

        val location: String get() = _location!!

        val homepageUri: String? get() = _homepageUri

        val companyImgUri: String get() = _companyImgUri!!
    }

}
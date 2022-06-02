package site.hirecruit.hr.domain.company.dto

import com.querydsl.core.annotations.QueryProjection

class CompanyDto {

    data class Info @QueryProjection constructor(
        val companyId: Long,
        val name: String,
        val location: String,
        val homepageUri: String?,
        val companyImgUri: String?
    )

    data class Create(
        val name: String,
        val location: String,
        val homepageUri: String?,
        val companyImgUri: String?
    )

}
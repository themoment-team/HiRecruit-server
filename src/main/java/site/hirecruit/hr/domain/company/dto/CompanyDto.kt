package site.hirecruit.hr.domain.company.dto

class CompanyDto {

    data class Info(
        val companyId: Long,
        val name: String,
        val location: String,
        val homepageUri: String?,
        val imageUri: String?
    )

    data class Create(
        val name: String,
        val location: String,
        val homepageUri: String?,
        val imageUri: String?
    )

}
package site.hirecruit.hr.domain.company.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.service.CompanyService

@RestController
@RequestMapping("/api/v1/company")
class CompanyController(
    private val companyService: CompanyService
) {

    private fun createCompany(createDto: CompanyDto.Create): CompanyDto.Info = companyService.create(createDto)

    @GetMapping
    private fun findAllCompany(): List<CompanyDto.Info> = companyService.findAllCompanies()

}
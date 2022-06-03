package site.hirecruit.hr.domain.company.controller

import org.apache.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.service.CompanyService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/company")
class CompanyController(
    private val companyService: CompanyService
) {

    @PostMapping
    private fun createCompany(@Valid @RequestBody createDto: CompanyDto.Create): ResponseEntity<CompanyDto.Info> =
        ResponseEntity.status(HttpStatus.SC_CREATED)
            .body(companyService.create(createDto))

    @GetMapping
    private fun findAllCompany(): ResponseEntity<List<CompanyDto.Info>> =
        ResponseEntity.ok(companyService.findAllCompanies())
}
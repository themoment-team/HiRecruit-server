package site.hirecruit.hr.domain.company.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.test_config.DataJpaTestConfig

@Import(DataJpaTestConfig::class)
@LocalTest
@DataJpaTest
internal class CompanyServiceTest(
    @Autowired private val companyRepository: CompanyRepository
){

    val companyService = CompanyService(companyRepository)

    @Test @DisplayName("company 생성 테스트")
    internal fun createTest() {
        // given
        val companyDto = CompanyDto.Create(
            _name = RandomString.make(8),
            _location = RandomString.make(10),
            _homepageUri = RandomString.make(15),
            _companyImgUri = RandomString.make(10)
        )

        // when
        val createdCompanyInfo = companyService.create(companyDto)

        // then
        assertAll({
            assertNotNull(createdCompanyInfo.companyId)
            assertEquals(companyDto.name, createdCompanyInfo.name)
            assertEquals(companyDto.location, createdCompanyInfo.location)
            assertEquals(companyDto.companyImgUri, createdCompanyInfo.companyImgUri)
        })
    }

    @Test
    @DisplayName("company 전체 조회 테스트")
    internal fun companyFindAll() {
        // given
        val fiveCompanyEntities = createMultipleCompanyEntity(5).map {
            CompanyDto.Info(
                companyId = it.companyId!!,
                name = it.name,
                location = it.location,
                homepageUri = it.homepageUri,
                companyImgUri = it.companyImgUri
            )
        }

        // when
        val result = companyService.findAllCompanies()

        // then
        assertIterableEquals(fiveCompanyEntities, result)
    }

    fun createMultipleCompanyEntity(count: Int): List<CompanyEntity>{
        val companyEntities: MutableList<CompanyEntity> = emptyList<CompanyEntity>().toMutableList()
        for(i: Int in 1..count){
            companyEntities.add(
                CompanyEntity(
                    name = RandomString.make(8),
                    location = RandomString.make(10),
                    homepageUri = RandomString.make(15),
                    companyImgUri = RandomString.make(10)
                )
            )
        }
        companyRepository.saveAll(companyEntities)
        return companyRepository.findAll() // Company entity의 Id generate 전략이 IDENTITY여서 다시 조회
    }

}
package site.hirecruit.hr.domain.company.service

import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.test_util.LocalTest
import site.hirecruit.hr.test_config.DataJpaTestConfig

@Import(DataJpaTestConfig::class)
@LocalTest
@DataJpaTest
@Transactional
internal class CompanyServiceTest(
    @Autowired private val companyRepository: CompanyRepository
){

    val companyService = CompanyService(companyRepository)

    @Test @DisplayName("company 생성 테스트")
    internal fun createTest() {
        // given
        val companyDto = CompanyDto.Create(
            name = RandomString.make(8),
            location = RandomString.make(10),
            homepageUri = RandomString.make(15),
            imageUri = RandomString.make(10)
        )

        // when
        val createdCompanyInfo = companyService.create(companyDto)

        // then
        assertAll({
            assertNotNull(createdCompanyInfo.companyId)
            assertEquals(companyDto.name, createdCompanyInfo.name)
            assertEquals(companyDto.location, createdCompanyInfo.location)
            assertEquals(companyDto.imageUri, createdCompanyInfo.imageUri)
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
                imageUri = it.imageUri
            )
        }

        // when
        val executeValue = companyService.findAllCompanies()

        // then
        assertIterableEquals(fiveCompanyEntities, executeValue)
    }

    fun createMultipleCompanyEntity(count: Int): List<CompanyEntity>{
        val companyEntities: MutableList<CompanyEntity> = emptyList<CompanyEntity>().toMutableList()
        for(i: Int in 1..count){
            companyEntities.add(
                CompanyEntity(
                    name = RandomString.make(8),
                    location = RandomString.make(10),
                    homepageUri = RandomString.make(15),
                    imageUri = RandomString.make(10)
                )
            )
        }
        return companyRepository.saveAll(companyEntities)
    }

}
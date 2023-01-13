package site.hirecruit.hr.domain.career.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.career.CareerEntity
import site.hirecruit.hr.domain.career.dto.CareerDto
import site.hirecruit.hr.domain.career.repository.CareerRepository
import site.hirecruit.hr.domain.company.dto.CompanyDto
import site.hirecruit.hr.domain.company.repository.CompanyRepository
import site.hirecruit.hr.domain.user.repository.UserRepository
import site.hirecruit.hr.global.util.YnType

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
@Service
class CareerService(
    @Autowired
    val careerRepository: CareerRepository,
    @Autowired
    val companyRepository: CompanyRepository,
    @Autowired
    val userRepository: UserRepository
) {

    fun createCareer(careerCreateRequestDto: CareerDto.CareerCreateRequestDto, userGithubId: Long): CareerDto.Info {
        val user = userRepository.findByGithubId(userGithubId)
        val company = companyRepository.findCompanyEntitiesByCompanyId(careerCreateRequestDto.companyId)

        val savedCareer = careerRepository.save(
            CareerEntity(
                careerId = null,
                userEntity = user ?: throw RuntimeException("HR에 회원 정보가 등록되지 않아 처리할 수 없음."),
                company = company ?: throw RuntimeException("HR에 회사가 등록되지 않아 처리할 수 없음."),
                position = careerCreateRequestDto.position,
                beginDate = careerCreateRequestDto.beginDate,
                endDate = careerCreateRequestDto.endDate,
                inOfficeYN = careerCreateRequestDto.inOfficeYN,
                disclosureStatus = careerCreateRequestDto.disclosureStatus,
                deleteStatus = YnType.N
            )
        )

        return CareerDto.Info(
            careerId = savedCareer.careerId ?: throw IllegalArgumentException("careerId는 null이 허용되지 않습니다."),
            companyInfo = company.run {
                CompanyDto.Info(
                        companyId = companyId!!,
                        name = name,
                        location = location,
                        homepageUri = homepageUri,
                        companyImgUri = companyImgUri
                    )
            },
            position = savedCareer.position,
            beginDate = savedCareer.beginDate,
            endDate = savedCareer.endDate,
            inOfficeYN = savedCareer.inOfficeYN,
            disclosureStatus = savedCareer.disclosureStatus,
            deleteStatus = savedCareer.deleteStatus
        )
    }

    fun getCareerInfoByMemberId(memberId: Long){

    }
}

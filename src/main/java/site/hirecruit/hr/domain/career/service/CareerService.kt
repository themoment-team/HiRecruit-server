package site.hirecruit.hr.domain.career.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import site.hirecruit.hr.domain.career.CareerEntity
import site.hirecruit.hr.domain.career.dto.CareerDto
import site.hirecruit.hr.domain.career.repository.CareerRepository
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

    fun createCareer(create: CareerDto.Create, userGithubId: Long): CareerDto.Info {
        val user = userRepository.findByGithubId(userGithubId) ?: throw RuntimeException("HR에 회원 정보가 등록되지 않아 처리할 수 없음.")
        val company = companyRepository.findCompanyEntitiesByCompanyId(create.companyId) ?: throw RuntimeException("HR에 회사가 등록되지 않아 처리할 수 없음.")

        return CareerDto.Info.of(
            careerRepository.save(
                CareerEntity(
                    careerId = null,
                    userEntity = user,
                    company = company,
                    position = create.position,
                    beginDate = create.beginDate,
                    endDate = create.endDate,
                    inOfficeYN = create.inOfficeYN,
                    disclosureStatus = create.disclosureStatus,
                    deleteStatus = YnType.N
                )
            )
        )
    }

    fun getCareerInfoByMemberId(memberId: Long){

    }
}

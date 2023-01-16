package site.hirecruit.hr.domain.career

import site.hirecruit.hr.domain.company.entity.CompanyEntity
import site.hirecruit.hr.domain.user.entity.UserEntity
import site.hirecruit.hr.global.util.YnType
import java.time.LocalDate
import javax.persistence.*

/**
 * @author 전지환 이용권&정산유닛 (jyeonjyan@sk.com)
 */
@Entity @Table(name = "career")
class CareerEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val careerId: Long? = null,

    @ManyToOne(targetEntity = UserEntity::class, fetch = FetchType.LAZY)
    val userEntity: UserEntity,

    @OneToOne(targetEntity = CompanyEntity::class, fetch = FetchType.LAZY)
    val company: CompanyEntity,

    @Column(name = "position")
    val position: String,

    @Column(name = "begin_date")
    val beginDate: LocalDate,

    @Column(name = "end_date")
    val endDate: LocalDate,

    @Column(name = "in_office_yn")
    val inOfficeYN: YnType,

    @Column(name = "disclosure_status")
    val disclosureStatus: YnType,

    @Column(name = "delete_status")
    val deleteStatus: YnType
){ }

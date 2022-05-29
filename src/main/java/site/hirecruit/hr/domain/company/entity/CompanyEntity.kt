package site.hirecruit.hr.domain.company.entity

import javax.persistence.*

/**
 * 회사의 정보를 담는 CompanyEntity
 *
 * @author 정시원
 * @since 1.0
 */
@Entity @Table(name = "company")
class CompanyEntity(
    @Column(name = "company_name", nullable = false)
    var name: String,

    @Column(name = "company_location", nullable = false)
    var location: String,

    @Column(name = "company_image_uri", nullable = true)
    var imageUri: String? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    val companyId: Long? = null
}
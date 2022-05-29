package site.hirecruit.hr.domain.worker.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import site.hirecruit.hr.domain.auth.entity.UserEntity
import site.hirecruit.hr.domain.company.entity.CompanyEntity
import javax.persistence.*

@Entity @Table(name = "worker")
class WorkerEntity(
    @Column(name = "companyName", nullable = false)
    var companyName: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "introduction", nullable = true)
    var introduction: String? = null,

    @Column(name = "give_link", nullable = true)
    var giveLink: String? = null,

    devYear: Int? = null,

    @Column(name = "position", nullable = true)
    var position: String? = null, //확장을 위해 enum 사용하지 않음 직군이 너무 많음

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "company_id")
    val company: CompanyEntity
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var workerId: Long? = null

    @Column(name = "dev_year", nullable = true)
    var devYear: Int? = devYear
        set(value) {
            if(value != null && value < 0)
                throw IllegalArgumentException("'devYear' property cannot have a negative number.")
            field = value
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WorkerEntity) return false

        if (workerId != other.workerId) return false

        return true
    }

    override fun hashCode(): Int {
        return workerId?.hashCode() ?: 0
    }


}
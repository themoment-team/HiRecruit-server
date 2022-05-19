package site.hirecruit.hr.domain.worker.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import site.hirecruit.hr.domain.auth.entity.UserEntity
import javax.persistence.*

@Entity @Table(name = "worker")
class WorkerEntity(
    @Column(name = "company", nullable = false)
    var company: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "introduction", nullable = true)
    var introduction: String? = null,

    @Column(name = "give_link", nullable = true)
    var giveLink: String? = null,

    devYear: Int? = null,

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: UserEntity
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
}
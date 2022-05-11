package site.hirecruit.hr.domain.worker.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import site.hirecruit.hr.domain.auth.entity.UserEntity
import javax.persistence.*

@Entity @Table(name = "worker")
class WorkerEntity(
    @Column(name = "github_id", nullable = false)
    val githubId: Long?,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "company", nullable = false)
    val company: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "introduction", nullable = true)
    val introduction: String? = null,

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: UserEntity
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var workerId: Long? = null


}
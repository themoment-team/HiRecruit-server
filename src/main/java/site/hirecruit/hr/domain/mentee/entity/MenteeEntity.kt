package site.hirecruit.hr.domain.mentee.entity

import site.hirecruit.hr.domain.user.entity.UserEntity
import javax.persistence.*

@Entity(name = "mentee")
class MenteeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_id")
    val menteeId: Long?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: UserEntity?,

    @Column(name = "mentee_certified")
    val certified: Boolean = false
) {

}
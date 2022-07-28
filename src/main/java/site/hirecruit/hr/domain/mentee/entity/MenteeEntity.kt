package site.hirecruit.hr.domain.mentee.entity

import site.hirecruit.hr.domain.user.entity.UserEntity
import javax.persistence.*

/**
 * satisfied v1.3 erd
 */
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
    var certified: Boolean = false
) {
    override fun toString(): String {
        return "MenteeEntity(menteeId=$menteeId, user=$user, certified=$certified)"
    }
}
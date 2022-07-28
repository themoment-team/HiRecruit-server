package site.hirecruit.hr.domain.mentor.entity

import site.hirecruit.hr.domain.user.entity.UserEntity
import javax.persistence.*

@Entity(name = "mentor")
class MentorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    val mentorId: Long?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: UserEntity?,

    @Column(name = "mentor_certified")
    var certified: Boolean = false,

    @Column(name = "donation_link")
    var donationLink: String?
) {

    override fun toString(): String {
        return "MentorEntity(mentorId=$mentorId, user=$user, certified=$certified, donationLink=$donationLink)"
    }
}
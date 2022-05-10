package site.hirecruit.hr.domain.mentee.entity

import javax.persistence.*

@Entity(name = "mentee")
class MenteeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentee_id")
    val menteeId: Long?,

    @Column(name = "mentee_uuid", unique = true)
    private val menteeUUID: String,

    @Column(name = "name")
    private val name: String,

    @Column(name = "email")
    private val email: String,

    @Column(name = "email_certified")
    private val emailCertified: Boolean = false
) {

    override fun toString(): String {
        return "MenteeEntity(menteeId=$menteeId, menteeUUID='$menteeUUID', name='$name', email='$email', emailCertified=$emailCertified)"
    }
}
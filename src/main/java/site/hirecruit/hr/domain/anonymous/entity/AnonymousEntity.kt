package site.hirecruit.hr.domain.anonymous.entity

import javax.persistence.*

@Entity(name = "anonymous")
class AnonymousEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anonymous_id")
    val anonymousId: Long?,

    @Column(name = "anonymous_uuid", unique = true)
    private val anonymousUUID: String,

    @Column(name = "name")
    private val name: String,

    @Column(name = "email")
    private val email: String,

    @Column(name = "email_certified")
    private val emailCertified: Boolean = false
) {

    override fun toString(): String {
        return "AnonymousEntity(anonymousId=$anonymousId, anonymousUUID='$anonymousUUID', name='$name', email='$email', emailCertified=$emailCertified)"
    }
}
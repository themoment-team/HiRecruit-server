package site.hirecruit.hr.domain.auth.entity

import javax.persistence.*

/**
 * 회원 Entity
 *
 * @author 정시원
 * @version 1.0
 */
@Entity @Table(name = "user")
class UserEntity(
    @Column(name = "github_id", nullable = false)
    val githubId: Long,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "profile_uri", nullable = false)
    val profileImgUri: String,

    @Column(name = "role", nullable = false) @Enumerated(EnumType.STRING)
    val role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null
}
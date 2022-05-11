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

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "profile_uri", nullable = false)
    val profileUri: String,

    @Column(name = "role", nullable = false)
    val role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null
}
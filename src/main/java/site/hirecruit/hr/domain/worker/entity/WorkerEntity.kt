package site.hirecruit.hr.domain.worker.entity

import javax.persistence.*

@Entity @Table(name = "worker")
class WorkerEntity(
    @Column(name = "github_id", nullable = false)
    val githubId: Long?,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "profile_uri", nullable = false)
    val profileUri: String,

    @Column(name = "company", nullable = false)
    val company: String,

    @Column(name = "location", nullable = false)
    var location: String,

    @Column(name = "introduction", nullable = true)
    val introduction: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var workerId: Long? = null

    fun updateRole(role: Role) {
        this.role = role
    }

    enum class Role(
        val role: String,
        val title: String
        ){
        GUEST("ROLE_GUEST", "게스트"),  // 인증하지 않은 직장인
        CLIENT("ROLE_CLIENT", "사용자");
    }
}
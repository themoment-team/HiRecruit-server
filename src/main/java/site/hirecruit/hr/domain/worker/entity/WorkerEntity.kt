package site.hirecruit.hr.domain.worker.entity

import javax.persistence.*

@Entity @Table(name = "worker")
class WorkerEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var workerId: Long? = null

    var githubId: Long? = null

    val email: String? = null

    var name: String? = null

    var profileUri: String? = null

    var company: String? = null

    var location: String? = null

    var introduction: String? = null

    @Enumerated(EnumType.STRING)
    var role: Role? = null

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
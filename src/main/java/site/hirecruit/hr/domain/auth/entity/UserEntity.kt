package site.hirecruit.hr.domain.auth.entity

import site.hirecruit.hr.domain.auth.dto.UserUpdateDto
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

    @Column(name = "github_login_id")
    var githubLoginId: String,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "profile_uri", nullable = false)
    val profileImgUri: String,

    @Column(name = "role", nullable = false) @Enumerated(EnumType.STRING)
    var role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long? = null

    fun updateRole(role: Role){
        this.role = role
    }

    fun update(updateDto: UserUpdateDto) = apply {
        this.email = updateDto.email!!
        this.name = updateDto.name!!
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEntity) return false

        if (githubId != other.githubId) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = githubId.hashCode()
        result = 31 * result + (userId?.hashCode() ?: 0)
        return result
    }


}
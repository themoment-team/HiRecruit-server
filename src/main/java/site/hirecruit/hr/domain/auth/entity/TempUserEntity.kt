package site.hirecruit.hr.domain.auth.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * 임시 유저를 저장하는 Entity
 *
 * @author 정시원
 */
@Entity @Table(name = "temp_user")
class TempUserEntity(

    @Id @Column(name = "github_id")
    val githubId: Long,

    @Column(name = "github_login_id")
    var githubLoginId: String,

    @Column(name = "profile_uri")
    val profileImgUri: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TempUserEntity) return false

        if (githubId != other.githubId) return false

        return true
    }

    override fun hashCode(): Int {
        return githubId.hashCode()
    }
}
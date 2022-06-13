package site.hirecruit.hr.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import javax.persistence.Column
/**
 * 임시 유저를 저장하는 Entity
 * Redis namespace는 temp_user를 사용하고 1시간의 유효기간을 갖습니다.
 *
 * @author 정시원
 */
@RedisHash(value = "temp_user", timeToLive = 60 * 60)
class TempUserEntity(
    @Id
    @Column(name = "github_id")
    val githubId: Long,

    @Column(name = "profile_uri")
    val profileImgUri: String,
) {

    @javax.persistence.Transient
    var role = Role.GUEST // 임시 유저의 권한은 GUEST이다.
        private set
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
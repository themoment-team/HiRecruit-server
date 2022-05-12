package site.hirecruit.hr.domain.auth.entity

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Column
import javax.persistence.Id

@RedisHash(value = "temp_user", timeToLive = 60 * 60) // 1시간의 유효기간을 갖는다.
class TempUserEntity(
    @Id
    @Column(name = "github_id")
    val githubId: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "profile_uri")
    val profileUri: String,
) {

    @javax.persistence.Transient
    val role = Role.GUEST // 임시 유저의 권한은 GUEST이다.
}
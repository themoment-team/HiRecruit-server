package site.hirecruit.hr.domain.mentor.verify.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

/**
 * 멘토가 되기 위해, 멘토 Id : 인증번호를 저장하는 엔티티 입니다.
 *
 * @since 1.2.0
 * @author 전지환
 */
@RedisHash(value = "verificationCode", timeToLive = 60 * 60 * 24) // 유효시간: 24시간
class MentorEmailVerificationCodeEntity(
    @Id
    private val workerId: Long,
    private val verificationCode: String
)
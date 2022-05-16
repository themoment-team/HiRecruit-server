package site.hirecruit.hr.domain.auth.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

/**
 * UserCustomRepository의 구현체 입니다.
 *
 * @since 1.0
 * @author 정시원
 */
@Repository
open class UserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): UserCustomRepository {

    override fun findUserAndWorkerEmailByGithubId(githubId: Long): AuthUserInfo {
        TODO("queryDSL feature 추가되면 작성할예정")
    }
}
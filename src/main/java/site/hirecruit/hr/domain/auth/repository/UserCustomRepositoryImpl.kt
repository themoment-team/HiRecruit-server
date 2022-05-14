package site.hirecruit.hr.domain.auth.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

class UserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): UserCustomRepository {

    override fun findUserAndWorkerEmailByGithubId(githubId: Long): AuthUserInfo {
        TODO("queryDSL feature 추가되면 작성할예정")
    }
}
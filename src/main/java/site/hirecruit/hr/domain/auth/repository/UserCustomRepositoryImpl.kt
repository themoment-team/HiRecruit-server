package site.hirecruit.hr.domain.auth.repository

import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.QAuthUserInfo
import site.hirecruit.hr.domain.auth.entity.QUserEntity.userEntity

/**
 * UserCustomRepository의 구현체 입니다.
 *
 * @since 1.0
 * @author 정시원
 */
@Repository
class UserCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): UserCustomRepository {

    override fun findUserAndWorkerEmailByGithubId(githubId: Long): AuthUserInfo? {
        return queryFactory
            .select(QAuthUserInfo(
                Expressions.constantAs(githubId, userEntity.githubId),
                userEntity.name,
                userEntity.email,
                userEntity.profileImgUri,
                userEntity.role
            ))
            .from(userEntity)
            .where(userEntity.githubId.eq(githubId))
            .fetchOne()
    }
}
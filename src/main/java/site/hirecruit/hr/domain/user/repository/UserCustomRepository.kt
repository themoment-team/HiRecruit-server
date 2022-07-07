package site.hirecruit.hr.domain.user.repository

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

/**
 * UserEntity의 CustomRepository입니다.
 *
 * @author 정시원
 * @since 1.0
 */
interface UserCustomRepository {

    /**
     * github_id로 UserEntity의 정보와 Worker.email를 가져옵니다.
     */
    fun findUserAndWorkerEmailByGithubId(githubId: Long): AuthUserInfo?
}
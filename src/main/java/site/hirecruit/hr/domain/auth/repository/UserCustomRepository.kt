package site.hirecruit.hr.domain.auth.repository

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

interface UserCustomRepository {

    /**
     * github_id로 UserEntity의 정보와 Worker.email를 가져옵니다.
     */
    fun findUserAndWorkerEmailByGithubId(githubId: Long): AuthUserInfo?
}
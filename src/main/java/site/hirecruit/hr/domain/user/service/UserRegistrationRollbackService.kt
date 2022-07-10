package site.hirecruit.hr.domain.user.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo

/**
 * 회원 등록 트랜젝션 실패시 수행할 Rollback Service
 *
 * @author 정시원
 * @since 1.0
 */
interface UserRegistrationRollbackService {

    /**
     * 회원 등록 트렌젝션이 실패하면 회원 데이터를 rollback한다.
     *
     * @return rollback에 성공한 AuthUserInfo
     */
    fun rollback(rollbackUserInfo: AuthUserInfo): AuthUserInfo
}
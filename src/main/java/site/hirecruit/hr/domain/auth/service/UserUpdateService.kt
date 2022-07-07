package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.UserUpdateDto

/**
 * User의 기본정보를 Update하는 Service
 * @author 정시원
 * @since 1.2
 */
interface UserUpdateService {

    /**
     * 사용자의 기본 정보를 수정한다.
     *
     * @return 수정된 사용자 정보 DTO
     */
    fun update(updateDto: UserUpdateDto, authUserInfo: AuthUserInfo): AuthUserInfo
}

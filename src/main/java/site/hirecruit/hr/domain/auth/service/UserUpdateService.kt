package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserUpdateDto

/**
 * User의 기본정보를 Update하는 Service
 */
interface UserUpdateService {

    fun update(updateDto: UserUpdateDto, authUserInfo: AuthUserInfo)
}
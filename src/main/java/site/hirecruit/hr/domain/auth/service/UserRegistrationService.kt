package site.hirecruit.hr.domain.auth.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.dto.UserRegistrationDto

/**
 * 회원가입을 진행합니다.
 *
 * @author 정시원
 * @version 1.0
 */
interface UserRegistrationService {

    /**
     * 임시 유저에 추가 정보를 입력하여 회원가입을 완료합니다.
     */
    fun registration(authUserInfo: AuthUserInfo, userRegistrationInfo: UserRegistrationDto)
}

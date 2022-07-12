package site.hirecruit.hr.domain.user.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.UserRegistrationDto

/**
 * 유저생성 서비스
 *
 * @author 정시원
 * @version 1.0
 */
@Deprecated("v1.3부터 더 이상 사용하지 않는 유저 등록 서비스 인터페이스 입니다.")
interface UserRegistrationService {

    /**
     * 유저를 생성합니다.
     *
     * @return [AuthUserInfo] - 임시 유저의 정보
     */
    fun registration(authUserInfo: AuthUserInfo, userRegistrationInfo: UserRegistrationDto): AuthUserInfo
}

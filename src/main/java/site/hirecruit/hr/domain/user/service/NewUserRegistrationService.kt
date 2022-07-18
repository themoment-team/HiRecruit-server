package site.hirecruit.hr.domain.user.service

import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.user.dto.CommonUserRegistrationDto

/**
 * 사용자 등록 서비스 인터페이스
 *
 * @author 정시원
 * @since 1.3
 */
interface NewUserRegistrationService<T : CommonUserRegistrationDto> {


    /**
     * 유저를 생성합니다.
     *
     * @return 생성된 사용자의 정보
     */
    fun registration(registrationDto : T): AuthUserInfo
}
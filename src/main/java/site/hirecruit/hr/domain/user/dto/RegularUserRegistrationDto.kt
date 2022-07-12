package site.hirecruit.hr.domain.user.dto

import site.hirecruit.hr.domain.user.vo.RegularUserRegistrationRequestVo

/**
 * 정식 사용자 등록 DTO
 *
 * @author 정시원
 * @since 1.3
 */
data class RegularUserRegistrationDto(
    override val githubId: Long,

    override val githubLoginId: String,

    override val profileImgUri: String,

    val userRegistrationInfo: RegularUserRegistrationRequestVo
) : CommonUserRegistrationDto {
}
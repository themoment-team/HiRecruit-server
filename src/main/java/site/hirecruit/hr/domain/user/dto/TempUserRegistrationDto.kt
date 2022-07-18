package site.hirecruit.hr.domain.user.dto

/**
 * 임시 사용자 등록 DTO
 *
 * @author 정시원
 * @since 1.3
 */
data class TempUserRegistrationDto(
    override val githubId: Long,
    override val githubLoginId: String,
    override val profileImgUri: String
) : CommonUserRegistrationDto

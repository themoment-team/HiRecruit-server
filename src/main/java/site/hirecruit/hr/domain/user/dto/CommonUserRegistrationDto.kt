package site.hirecruit.hr.domain.user.dto

/**
 * 사용자 등록 로직에 공통으로 사용되는 Data를 정의한 Maker interface
 *
 * @author 정시원
 * @since 1.3
 *
 */
interface CommonUserRegistrationDto {
    val githubId: Long
    val githubLoginId: String
    val profileImgUri: String
}
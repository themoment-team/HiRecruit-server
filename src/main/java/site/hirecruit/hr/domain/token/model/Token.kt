package site.hirecruit.hr.domain.token.model

/**
 * Token인증에 필요한 token을 저장하는 데이터 클래스
 *
 * @author 정시원
 * @since 1.0
 */
data class Token(
    val accessToken: String,
    val refreshToken: String
) {
}
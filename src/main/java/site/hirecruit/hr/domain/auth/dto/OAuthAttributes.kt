package site.hirecruit.hr.domain.auth.dto

/**
 * Oauth2 클라이언트의 정보를 담고 있는 객체
 * ## notice
 * 해당 클래스는 생성자를 통해 객체를 생성할 수 없습니다. 대신 정적 팩토리 메서드([OAuthAttributes.of])를 사용해주세요
 *
 * ### example
 * ```kotlin
 * val oAuthAttributes = OAuthAttributes.of(
 *     registrationId,
 *     userNameAttributeName,
 *     oAuth2User.attributes
 * )
 * ```
 * @author 정시원
 */
class OAuthAttributes private constructor(
    val attributes: Map<String, Any>,
    val userNameAttributeName: String,
    val id: Long,
    val name: String,
    val email: String,
    val profileImgUri: String
) {

    companion object {
        fun of(
            registrationId : String,
            userNameAttributeName : String,
            attributes: Map<String, Any>
        ): OAuthAttributes{
            return ofGithub(userNameAttributeName, attributes)
        }

        private fun ofGithub(userNameAttributeName: String,
                             attributes: Map<String, Any>): OAuthAttributes {
            return OAuthAttributes(
                attributes = attributes,
                userNameAttributeName = userNameAttributeName,
                id = attributes[userNameAttributeName] as Long,
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                profileImgUri = attributes["avatar_url"] as String
            )
        }
    }
}
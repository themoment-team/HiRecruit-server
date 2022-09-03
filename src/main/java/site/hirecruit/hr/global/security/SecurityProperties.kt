package site.hirecruit.hr.global.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * security 관련 properties를 바인딩 한 클래스
 * resources/properties/security.yml 참고
 *
 * @author 정시원
 * @since v1.3.0
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "security")
data class SecurityProperties(
    val oauth2: OAuth2Properties
) {

    data class OAuth2Properties(
        val loginEndpointBaseUri: String,
        val loginRedirectionEndpointBaseUri: String
    )
}
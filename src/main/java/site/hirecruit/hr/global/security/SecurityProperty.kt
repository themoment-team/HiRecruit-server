package site.hirecruit.hr.global.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "security")
data class SecurityProperty(
    val oauth2: OAuth2Properties
) {

    data class OAuth2Properties(
        val loginEndpointBaseUri: String,
        val loginRedirectionEndpointBaseUri: String
    )
}
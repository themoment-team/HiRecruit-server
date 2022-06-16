package site.hirecruit.hr.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import site.hirecruit.hr.global.data.ServerProfile.Companion.DEFAULT
import site.hirecruit.hr.global.data.ServerProfile.Companion.LOCAL
import site.hirecruit.hr.global.data.ServerProfile.Companion.PROD
import site.hirecruit.hr.global.data.ServerProfile.Companion.STAGING

/**
 * CorsConfiguration
 *
 * @author 정시원
 * @since 1.0
 */
@Configuration
class WebMvcCorsConfig(
    @Value("\${hr.domain}") private val hrDomain: String
) {

    /**
     * 운영환경(prod)에서 활성화 되는 WebMvcConfigurer
     */
    @Configuration
    @Profile(PROD)
    inner class ProdRole: WebMvcConfigurer{
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedOrigins("https://www.hirecruit.site")
                .allowedMethods(*allowedHttpMethod())
                .allowCredentials(true)
                .maxAge(3000);
        }
    }

    /**
     * 테스팅환경(DEFAULT, LOCAL, STAGING)에서 활성화 되는 WebMvcConfigurer
     */
    @Configuration
    @Profile(DEFAULT, LOCAL, STAGING)
    inner class TestingRole: WebMvcConfigurer{
        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedOrigins(
                    "http://localhost:3000", "https://localhost:3000",
                    "http://dev.${hrDomain}", "https://dev.${hrDomain}"
                )
                .allowedMethods(*allowedHttpMethod())
                .allowCredentials(true)
                .maxAge(3000);
        }
    }

    private fun allowedHttpMethod(): Array<String> =
        arrayOf(
            HttpMethod.GET.name,
            HttpMethod.POST.name,
            HttpMethod.PATCH.name,
            HttpMethod.PUT.name,
            HttpMethod.DELETE.name,
            HttpMethod.HEAD.name,
            HttpMethod.OPTIONS.name,
        )
}
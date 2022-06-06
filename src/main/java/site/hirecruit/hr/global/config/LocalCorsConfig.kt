package site.hirecruit.hr.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@Profile("local", "prod-test")
class LocalCorsConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000", "https://localhost:3000",
                "https://stg.hirecruit.site"
            )
            .allowedMethods(
                HttpMethod.GET.name,
                HttpMethod.POST.name,
                HttpMethod.PATCH.name,
                HttpMethod.PUT.name,
                HttpMethod.DELETE.name,
                HttpMethod.HEAD.name,
                HttpMethod.OPTIONS.name,
                HttpMethod.TRACE.name
            )
            .allowCredentials(true)
            .maxAge(3000);
    }
}
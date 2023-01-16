package site.hirecruit.hr.global.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import site.hirecruit.hr.global.data.ServerProfile
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@Profile(ServerProfile.DEFAULT, ServerProfile.LOCAL, ServerProfile.STAGING)
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage("site.hirecruit.hr.domain"))
            .paths(PathSelectors.ant("/api/**"))
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("HiRecruit API SERVER")
            .description("HiRecruit API DOC")
            .version("1.0")
            .build()
    }
}

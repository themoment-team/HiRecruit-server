package site.hirecruit.hr.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import site.hirecruit.hr.global.resolver.CurrentAuthUserInfoResolver


@Configuration
class WebConfig(
    private val currentAuthUserInfoResolver: CurrentAuthUserInfoResolver
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(currentAuthUserInfoResolver)
    }
}
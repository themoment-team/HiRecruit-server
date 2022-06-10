package site.hirecruit.hr.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import site.hirecruit.hr.global.resolver.CurrentAuthUserInfoResolver

/**
 * WebMVC ArgumentResolver를 관리하는 Config
 *
 * @since 1.0
 * @author 정시원
 */
@Configuration
class WebMvcArgumentResolversConfig(
    private val currentAuthUserInfoResolver: CurrentAuthUserInfoResolver
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(currentAuthUserInfoResolver)
    }
}
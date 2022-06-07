package site.hirecruit.hr.global.config

import org.springframework.boot.web.server.Cookie
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.web.http.CookieSerializer
import org.springframework.session.web.http.DefaultCookieSerializer

/**
 * cookie에 대한 설정을 하는 class입니다.
 *
 * @author 정시원
 * @since 1.0
 */
@Configuration
class CookieConfig {

    @Bean
    fun cookieSerializer(): CookieSerializer? {
        val serializer = DefaultCookieSerializer()
        serializer.setCookieName("HRSESSION")
        serializer.setSameSite(Cookie.SameSite.NONE.attributeValue())
        serializer.setDomainName("www.hirecruit.site")
        return serializer
    }

}
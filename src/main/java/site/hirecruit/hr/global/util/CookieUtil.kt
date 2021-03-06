package site.hirecruit.hr.global.util

import org.springframework.stereotype.Component
import javax.servlet.http.Cookie

/**
 * cookie 생성 util class
 *
 * @author 정시원
 * @since 1.1.1
 */
class CookieUtil{

    companion object{
        fun userTypeCookie(value: String, hrDomain: String): Cookie{
            val userTypeCookie = Cookie("USER_TYPE", value)
            userTypeCookie.maxAge = 86400
            userTypeCookie.path = "/"
            userTypeCookie.domain = hrDomain
            return userTypeCookie
        }

        fun deleteUserTypeCookie(domain: String): Cookie{
            val userTypeCookie = Cookie("USER_TYPE", null)
            userTypeCookie.maxAge = 0
            userTypeCookie.domain = domain
            userTypeCookie.path = "/"
            return userTypeCookie
        }

        fun deleteHrsessionCookie(domain: String): Cookie{
            val hrsessionCookie = Cookie("HRSESSION", null)
            hrsessionCookie.secure = true
            hrsessionCookie.maxAge = 0 // 쿠키 삭제하게 위해 maxAge 0으로 설정
            hrsessionCookie.isHttpOnly = true
            hrsessionCookie.domain = domain
            hrsessionCookie.path = "/"
            return hrsessionCookie
        }
    }
}
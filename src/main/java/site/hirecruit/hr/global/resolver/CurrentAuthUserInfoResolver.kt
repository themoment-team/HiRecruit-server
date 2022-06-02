package site.hirecruit.hr.global.resolver

import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import site.hirecruit.hr.global.annotation.CurrentAuthUserInfo
import site.hirecruit.hr.global.data.SessionAttribute
import javax.servlet.http.HttpSession

/**
 * [CurrentAuthUserInfo] annotation을 통해 controller에서 Session정보를 가져오는 [HandlerMethodArgumentResolver]
 *
 * @author 정시원
 * @since 1.0
 */
@Component
class CurrentAuthUserInfoResolver(
    private val httpSession: HttpSession
): HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CurrentAuthUserInfo::class.java);
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        return httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName)
            ?: throw HttpClientErrorException(
                HttpStatus.UNAUTHORIZED,
                "Unauthorization; User information not found. Check your login status"
            )
    }


}
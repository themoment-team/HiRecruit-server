package site.hirecruit.hr.domain.auth.aop

import mu.KotlinLogging
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.global.data.SessionAttribute
import javax.servlet.http.HttpSession

val log = KotlinLogging.logger {}

/**
 * UserAuth관련 AOP
 *
 * @author 정시원
 * @since 1.0
 */
@Component
@Aspect
private open class UserAuthAspect (
    private val httpSession: HttpSession
) {

    @Pointcut("execution(* site.hirecruit.hr.domain.auth.service.UserAuthService+.authentication(..))")
    private fun userAuthService_AuthenticationMethodPointCut(){}

    /**
     * [site.hirecruit.hr.domain.auth.service.UserAuthService.authentication]에서의 유저 인증 수행 후 세션을 발급하는 AOP method
     */
    @AfterReturning(
        "userAuthService_AuthenticationMethodPointCut()",
        returning = "authUserInfo"
    )
    private fun setSessionByAuthUserInfo(authUserInfo: AuthUserInfo): Any{
        log.debug("UserAuthAspect.setSessionByAuthUserInfo active")
        httpSession.setAttribute(SessionAttribute.AUTH_USER_INFO.attributeName, authUserInfo)
        log.debug("session id='${httpSession.id}', authUserInfo='${httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName)}'")
        return authUserInfo
    }

}
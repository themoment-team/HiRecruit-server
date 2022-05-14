package site.hirecruit.hr.domain.auth.aop

import mu.KotlinLogging
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import javax.servlet.http.HttpSession

val log = KotlinLogging.logger {}

@Component
@Aspect
open class UserAuthAspect (
    private val httpSession: HttpSession
) {

    @Pointcut("execution(* site.hirecruit.hr.domain.auth.service.UserAuthService+.authentication(..))")
    fun userAuthService_AuthenticationMethodPointCut(){}

    @AfterReturning(
        "userAuthService_AuthenticationMethodPointCut()",
        returning = "authUserInfo"
    )
    fun setSessionByAuthUserInfo(authUserInfo: AuthUserInfo): Any{
        log.debug("UserAuthAspect.setSessionByAuthUserInfo active")
        httpSession.setAttribute("authUserInfo", authUserInfo)
        log.debug("session id='${httpSession.id}', authUserInfo='${httpSession.getAttribute("authUserInfo")}'")
        return authUserInfo
    }

}
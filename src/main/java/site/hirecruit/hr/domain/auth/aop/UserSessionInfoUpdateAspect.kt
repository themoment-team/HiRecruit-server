package site.hirecruit.hr.domain.auth.aop

import mu.KotlinLogging
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.global.data.SessionAttribute
import javax.servlet.http.HttpSession

private val log = KotlinLogging.logger {}

/**
 * session에 저장된 AuthUserInfo 저장 Aspect
 *
 * @author 정시원
 * @since 1.0
 */
@Component
@Aspect
class UserSessionInfoUpdateAspect (
    private val httpSession: HttpSession
) {

    @Pointcut("execution(* site.hirecruit.hr.domain.auth.service.UserAuthService+.authentication(..))")
    private fun userAuthService_AuthenticationMethodPointCut(){}

    @Pointcut("execution(* site.hirecruit.hr.domain.auth.service.UserRegistrationRollbackService+.rollback(..))")
    private fun userRegistrationRollbackService_rollback(){}

    /**
     * [site.hirecruit.hr.domain.auth.service.UserAuthService.authentication]에서의 유저 인증 수행 후 세션을 발급하는 AOP method
     */
    @AfterReturning(
        "userAuthService_AuthenticationMethodPointCut() || userRegistrationRollbackService_rollback()",
        returning = "authUserInfo"
    )
    private fun setSessionByAuthUserInfo(authUserInfo: AuthUserInfo): Any{
        log.debug("UserAuthAspect.setSessionByAuthUserInfo active")
        httpSession.setAttribute(SessionAttribute.AUTH_USER_INFO.attributeName, authUserInfo)
        log.debug("session id='${httpSession.id}', authUserInfo='${httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName)}'")
        return authUserInfo
    }

}
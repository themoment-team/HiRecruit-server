package site.hirecruit.hr.domain.auth.aop

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.repository.TempUserRepository
import site.hirecruit.hr.global.data.SessionAttribute
import javax.servlet.http.HttpSession

/**
 * [site.hirecruit.hr.domain.auth.service.UserRegistrationService]의 횡단 관심사를 모아놓은 AOP 클래스
 *
 * @author 정시원
 * @since 1.0
 * @see site.hirecruit.hr.domain.auth.service.UserRegistrationService
 */
@Component
@Aspect
private open class UserRegistrationAspect(
    private val tempUserRepository: TempUserRepository,
    private val httpSession: HttpSession
) {

    @Pointcut("execution(* site.hirecruit.hr.domain.auth.service.UserRegistrationService+.registration(..))")
    private fun userRegistrationService_registrationMethodPointCut(){}

    /**
     * 회원가입 후 진행해야 하는 횡단 관심사
     * 1. 임시유저 제거
     * 2. 세션에 유저 인증 정보 업데이트
     */
    @AfterReturning(
        "userRegistrationService_registrationMethodPointCut()",
        returning = "authUserInfo"
    )
    fun afterRegistrationMethod(authUserInfo: AuthUserInfo){
        deleteTempUser(authUserInfo.githubId)
        sessionUserAuthInfoUpdate(authUserInfo)
    }

    private fun deleteTempUser(githubId: Long){
        tempUserRepository.deleteById(githubId)
    }

    private fun sessionUserAuthInfoUpdate(authUserInfo: AuthUserInfo){
        httpSession.setAttribute(SessionAttribute.AUTH_USER_INFO.attributeName, authUserInfo)
    }

}
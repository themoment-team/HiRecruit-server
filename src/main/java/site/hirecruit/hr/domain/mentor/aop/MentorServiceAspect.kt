package site.hirecruit.hr.domain.mentor.aop

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.dto.AuthUserInfo
import site.hirecruit.hr.domain.auth.entity.Role
import site.hirecruit.hr.domain.auth.service.SecurityContextAccessService
import site.hirecruit.hr.global.data.SessionAttribute
import javax.servlet.http.HttpSession

/**
 * MentorService에 대한 Aspect class
 *
 * @author 정시원
 * @since 1.2
 */
@Component
@Aspect
class MentorServiceAspect(
    private val httpSession: HttpSession,
    private val securityContextAccessService: SecurityContextAccessService
) {

    @Pointcut("execution(* site.hirecruit.hr.domain.mentor.service.MentorService+.grantMentorRole(..))")
    fun mentorService_grantMentorRolePointcut(){}

    /**
     * Mentor의 AuthUserInfo를 세션에 등록하고, Role를 Spring Security에 동기화한다.
     *
     * @see site.hirecruit.hr.domain.mentor.service.MentorService.grantMentorRole
     */
    @AfterReturning("mentorService_grantMentorRolePointcut()")
    fun updateSessionAndSecurityContextByMentorAuthUserInfo(){
        val authUserInfoBeforeMentor = httpSession.getAttribute(SessionAttribute.AUTH_USER_INFO.attributeName) as AuthUserInfo

        val authUserInfoAfterMentor = AuthUserInfo(
            githubId = authUserInfoBeforeMentor.githubId,
            githubLoginId = authUserInfoBeforeMentor.githubLoginId,
            name = authUserInfoBeforeMentor.name,
            email = authUserInfoBeforeMentor.email,
            profileImgUri = authUserInfoBeforeMentor.profileImgUri,
            role = Role.MENTOR
        )

        setMentorInfoInSession(authUserInfoAfterMentor)
        updateUserRoleInSecurityContext(authUserInfoAfterMentor)
    }

    private fun updateUserRoleInSecurityContext(authUserInfo: AuthUserInfo) {
        securityContextAccessService.updateAuthentication(authUserInfo)
    }

    private fun setMentorInfoInSession(mentorAuthUserInfo: AuthUserInfo){
        httpSession.setAttribute(SessionAttribute.AUTH_USER_INFO.attributeName, mentorAuthUserInfo)
    }

}
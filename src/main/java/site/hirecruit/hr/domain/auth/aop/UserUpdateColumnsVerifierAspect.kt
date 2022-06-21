package site.hirecruit.hr.domain.auth.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import site.hirecruit.hr.domain.auth.dto.UserUpdateDto

/**
 * UserUpdateDto필드의 값을 유요한지 확인하는 AOP
 *
 * @author 정시원
 * @since 1.0
 */
@Component
@Aspect
class UserUpdateColumnsVerifierAspect {

    @Pointcut("execution(* site.hirecruit.hr.domain.auth.service.UserUpdateService+.update(..))")
    private fun authUserWorkerService_updateMethodPointCut(){}

    @Before("authUserWorkerService_updateMethodPointCut()")
    fun validationUserUpdateDto(joinPoint: JoinPoint){
        joinPoint.args.asList().forEach {
            if(it is UserUpdateDto){
                verifyUpdateDtoProperty(it)
                return@forEach
            }
        }
    }

    fun verifyUpdateDtoProperty(updateDto: UserUpdateDto){
        updateDto.updateColumns.forEach {
            when(it) {
                UserUpdateDto.Column.EMAIL      -> verifyEmailColumn(updateDto.email)
                UserUpdateDto.Column.NAME       -> verifyNameColumn(updateDto.name)
            }
        }
    }

    private fun verifyEmailColumn(email: String?){
        if(email == null) throw IllegalArgumentException("email : must not be null.")
        if(email.isBlank()) throw IllegalArgumentException("email : must not be blank.")
    }

    private fun verifyNameColumn(name: String?){
        if(name == null) throw IllegalArgumentException("name : must not be null.")
        if(name.isBlank()) throw IllegalArgumentException("name : must not be blank.")
    }
}